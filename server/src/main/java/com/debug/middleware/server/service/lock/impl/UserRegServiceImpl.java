package com.debug.middleware.server.service.lock.impl;

import com.debug.middleware.model.entity.UserReg;
import com.debug.middleware.model.mapper.UserRegMapper;
import com.debug.middleware.server.dto.UserRegDTO;
import com.debug.middleware.server.service.lock.UserRegService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/25
 */
@Service
public class UserRegServiceImpl implements UserRegService {

    private final UserRegMapper userRegMapper;
    private final StringRedisTemplate stringRedisTemplate;

    public UserRegServiceImpl(UserRegMapper userRegMapper, StringRedisTemplate stringRedisTemplate) {
        this.userRegMapper = userRegMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 用户注册 - 不加分布式锁
     *
     * @param dto
     */
    @Override
    @Transactional
    public void userRegNoLock(UserRegDTO dto) {
        checkAndSaveUser(dto);
    }

    /**
     * 用户注册 - 加分布式锁
     *
     * @param dto
     */
    @Override
    @Transactional
    public void userRegWithLock(UserRegDTO dto) {
        final String key = dto.getUsername() + ":lock";
        final String value = System.nanoTime() + UUID.randomUUID().toString();
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

        Boolean res = valueOperations.setIfAbsent(key, value);
        if (res != null && res) {
            stringRedisTemplate.expire(key, 20L, TimeUnit.SECONDS);

            try {
                checkAndSaveUser(dto);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                if (value.equals(valueOperations.get(key))) {
                    stringRedisTemplate.delete(key);
                }
            }
        }
    }

    /**
     * 保存用户
     *
     * @param dto
     */
    private void checkAndSaveUser(UserRegDTO dto) {
        UserReg userReg = userRegMapper.selectByUsername(dto.getUsername());
        if (userReg != null) {
            throw new RuntimeException("用户信息已存在");
        }

        UserReg entity = new UserReg();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setCreateTime(LocalDateTime.now());
        userRegMapper.save(entity);
    }
}
