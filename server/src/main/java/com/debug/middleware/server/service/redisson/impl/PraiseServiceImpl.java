package com.debug.middleware.server.service.redisson.impl;

import com.debug.middleware.model.dto.PraiseRankDTO;
import com.debug.middleware.model.entity.Praise;
import com.debug.middleware.model.mapper.PraiseMapper;
import com.debug.middleware.server.dto.PraiseDTO;
import com.debug.middleware.server.service.redisson.PraiseService;
import com.debug.middleware.server.service.redisson.RedisPraiseService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/27
 */
@Slf4j
@Service
public class PraiseServiceImpl implements PraiseService {

    private static final String ADD_PRAISE_KEY = "AddPraiseLock";
    private final PraiseMapper praiseMapper;
    private final RedisPraiseService redisPraiseService;
    private final RedissonClient redissonClient;

    public PraiseServiceImpl(PraiseMapper praiseMapper, RedisPraiseService redisPraiseService, RedissonClient redissonClient) {
        this.praiseMapper = praiseMapper;
        this.redisPraiseService = redisPraiseService;
        this.redissonClient = redissonClient;
    }

    /**
     * 点赞 - 无锁
     *
     * @param dto 请求信息
     */
    @Override
    @Transactional
    public void addPraise(PraiseDTO dto) throws Exception {
        Praise praise = praiseMapper.selectByBlogUserId(dto.getUserId(), dto.getBlogId());
        if (praise == null) {
            int count = toPraise(dto);

            // 缓存点赞记录
            if (count > 0) {
                log.info("点赞博客-{}-无锁-插入点赞记录成功", dto.getBlogId());
                redisPraiseService.cachePraiseBlog(dto.getBlogId(), dto.getUserId(), 1);
                // 排行榜
                this.cachePraiseTotal();
            }
        }

    }

    /**
     * 点赞 - 分布式锁 - 针对同一用户高并发重复点赞的情况
     *
     * @param dto 请求信息
     */
    @Override
    @Transactional
    public void addPraiseLock(PraiseDTO dto) throws Exception {
        final String lockName = ADD_PRAISE_KEY + dto.getBlogId() + "-" + dto.getUserId();
        // 获取一次性锁对象
        RLock lock = redissonClient.getLock(lockName);
        // 上锁并10s自动释放，避免redis节点宕机时出现死锁
        lock.lock(10, TimeUnit.SECONDS);
        try {
            Praise praise = praiseMapper.selectByBlogUserId(dto.getUserId(), dto.getBlogId());
            if (praise == null) {
                int count = toPraise(dto);

                // 缓存点赞记录
                if (count > 0) {
                    log.info("点赞博客-{}-分布式锁-插入点赞记录成功", dto.getBlogId());
                    redisPraiseService.cachePraiseBlog(dto.getBlogId(), dto.getUserId(), 1);
                    // 排行榜
                    this.cachePraiseTotal();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public void cancelPraise(PraiseDTO dto) throws Exception {
        if (null != dto.getBlogId() && null != dto.getUserId()) {
            int result = praiseMapper.cancelPraise(dto.getBlogId(), dto.getUserId());
            if (result > 0) {
                log.info("取消点赞博客-{}-更新点赞记录成功", dto.getBlogId());
                redisPraiseService.cachePraiseBlog(dto.getBlogId(), dto.getUserId(), 0);
                // 排行榜
                this.cachePraiseTotal();
            }
        }
    }

    @Override
    public Long getBlogPraiseTotal(Integer blogId) {
        return redisPraiseService.getCacheTotalBlog(blogId);
    }

    @Override
    public Collection<PraiseRankDTO> getRankNoRedisson() {
        return praiseMapper.getPraiseRank();
    }

    @Override
    public Collection<PraiseRankDTO> getRankWithRedisson() {
        return redisPraiseService.getBlogPraiseRank();
    }

    private int toPraise(PraiseDTO dto) {
        Praise praise;
        praise = new Praise();
        praise.setBlogId(dto.getBlogId());
        praise.setUserId(dto.getUserId());
        praise.setStatus(1);
        praise.setIsActive(1);
        praise.setPraiseTime(LocalDateTime.now());
        praise.setCreateTime(LocalDateTime.now());
        return praiseMapper.save(praise);
    }

    private void cachePraiseTotal() {
        redisPraiseService.rankBlogPraise();
    }
}
