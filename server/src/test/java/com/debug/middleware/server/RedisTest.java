package com.debug.middleware.server;

import com.debug.middleware.server.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/9
 */
@Slf4j
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void one() {
        log.info("---------------开始--------------");
        final String key = "redis:template:one:string";
        final String content = "RedisTemplate字符串";
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 写入
        valueOperations.set(key, content);
        // 读取
        Object result = valueOperations.get(key);
        log.info("result: {}", result);
    }

    @Test
    public void two() throws JsonProcessingException {
        User user = new User(1, "debug", "阿修罗");
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        final String key = "redis:template:two:object";
        final String content = objectMapper.writeValueAsString(user);
        valueOperations.set(key, content);
        Object result = valueOperations.get(key);
        if (result != null) {
            User resultUser = objectMapper.readValue(result.toString(), User.class);
            log.info("result: {}", resultUser);
        }
    }

    @Test
    public void three() {

    }
}
