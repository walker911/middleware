package com.debug.middleware.server.service.redisson.impl;

import com.debug.middleware.model.dto.PraiseRankDTO;
import com.debug.middleware.model.entity.Praise;
import com.debug.middleware.model.mapper.PraiseMapper;
import com.debug.middleware.server.dto.PraiseDTO;
import com.debug.middleware.server.service.redisson.PraiseService;
import com.debug.middleware.server.service.redisson.RedisPraiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

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

    private final PraiseMapper praiseMapper;
    private final RedisPraiseService redisPraiseService;

    public PraiseServiceImpl(PraiseMapper praiseMapper, RedisPraiseService redisPraiseService) {
        this.praiseMapper = praiseMapper;
        this.redisPraiseService = redisPraiseService;
    }

    /**
     * 点赞 - 无锁
     *
     * @param dto
     */
    @Override
    @Transactional
    public void addPraise(PraiseDTO dto) throws Exception {
        Praise praise = praiseMapper.selectByBlogUserId(dto.getUserId(), dto.getBlogId());
        if (praise == null) {
            praise = new Praise();
            praise.setBlogId(dto.getBlogId());
            praise.setUserId(dto.getUserId());
            praise.setStatus(1);
            praise.setIsActive(1);
            praise.setPraiseTime(LocalDateTime.now());
            praise.setCreateTime(LocalDateTime.now());
            int count = praiseMapper.save(praise);

            // 缓存点赞记录
            if (count > 0) {
                log.info("点赞博客-{}-无锁-插入点赞记录成功", dto.getBlogId());
                redisPraiseService.cachePraiseBlog(dto.getBlogId(), dto.getUserId(), 1);
            }
        }

    }

    @Override
    public void addPraiseLock(PraiseDTO dto) {

    }

    @Override
    @Transactional
    public void cancelPraise(PraiseDTO dto) throws Exception {
        if (null != dto.getBlogId() && null != dto.getUserId()) {
            int result = praiseMapper.cancelPraise(dto.getBlogId(), dto.getUserId());
            if (result > 0) {
                log.info("取消点赞博客-{}-更新点赞记录成功", dto.getBlogId());
                redisPraiseService.cachePraiseBlog(dto.getBlogId(), dto.getUserId(), 0);
            }
        }
    }

    @Override
    public Long getBlogPraiseTotal(Integer blogId) {
        return redisPraiseService.getCacheTotalBlog(blogId);
    }

    @Override
    public Collection<PraiseRankDTO> getRankNoRedisson() {
        return null;
    }

    @Override
    public Collection<PraiseRankDTO> getRankWithRedisson() {
        return null;
    }
}
