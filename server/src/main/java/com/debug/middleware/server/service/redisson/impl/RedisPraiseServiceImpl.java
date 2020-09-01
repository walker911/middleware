package com.debug.middleware.server.service.redisson.impl;

import com.debug.middleware.model.dto.PraiseRankDTO;
import com.debug.middleware.model.mapper.PraiseMapper;
import com.debug.middleware.server.service.redisson.RedisPraiseService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
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
public class RedisPraiseServiceImpl implements RedisPraiseService {

    private static final String BLOG_KEY = "RedisBlogPraiseMap";
    private final RedissonClient redissonClient;
    private final PraiseMapper praiseMapper;

    public RedisPraiseServiceImpl(RedissonClient redissonClient, PraiseMapper praiseMapper) {
        this.redissonClient = redissonClient;
        this.praiseMapper = praiseMapper;
    }

    @Override
    public void cachePraiseBlog(Integer blogId, Integer userId, Integer status) throws Exception {
        final String lockName = new StringBuffer("BlogRedissonPraiseLock").append(blogId).append(userId).append(status).toString();
        RLock lock = redissonClient.getLock(lockName);
        // 可重入锁
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        try {
            if (res) {
                if (blogId != null && userId != null && status != null) {
                    final String key = blogId + ":" + userId;
                    RMap<String, Integer> praiseMap = redissonClient.getMap(BLOG_KEY);
                    if (1 == status) {
                        praiseMap.putIfAbsent(key, userId);
                    } else if (0 == status) {
                        praiseMap.remove(key);
                    }
                }
            }
        } finally {
            lock.forceUnlock();
        }
    }

    @Override
    public Long getCacheTotalBlog(Integer blogId) {
        long result = 0;

        if (blogId != null) {
            RMap<String, Integer> praiseMap = redissonClient.getMap(BLOG_KEY);

            Map<String, Integer> dataMap = praiseMap.readAllMap();
            if (dataMap != null) {
                for (String s : dataMap.keySet()) {
                    String[] arr = s.split(":");
                    Integer bId = Integer.parseInt(arr[0]);
                    if (blogId.equals(bId)) {
                        result++;
                    }
                }
            }
        }

        return result;
    }

    @Override
    public void rankBlogPraise() {
        final String key = "PraiseRankListKey";

        List<PraiseRankDTO> dtos = praiseMapper.getPraiseRank();
        if (!CollectionUtils.isEmpty(dtos)) {
            RList<PraiseRankDTO> list = redissonClient.getList(key);
            list.clear();
            list.addAll(dtos);
        }
    }

    @Override
    public List<PraiseRankDTO> getBlogPraiseRank() {
        final String key = "PraiseRankListKey";
        RList<PraiseRankDTO> list = redissonClient.getList(key);

        return list.readAll();
    }
}
