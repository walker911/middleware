package com.debug.middleware.server;

import com.debug.middleware.server.dto.BloomDTO;
import com.debug.middleware.server.dto.UserLoginDTO;
import com.debug.middleware.server.service.redisson.UserLoginPublisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * <p>
 * Redisson Test
 * </p>
 *
 * @author mu qin
 * @date 2020/8/26
 */
@Slf4j
@SpringBootTest
public class RedissonTest {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private UserLoginPublisher userLoginPublisher;

    @Test
    public void testConnect() throws IOException {
        log.info("redisson的配置信息：{}", redissonClient.getConfig().toYAML());
    }

    @Test
    public void testBloomFilter() {
        final String key = "bloom:filter:data:v1";
        long total = 100000;

        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter(key);
        bloomFilter.tryInit(total, 0.01);
        for (int i = 1; i < total; i++) {
            bloomFilter.add(i);
        }

        log.info("是否包含1：{}", bloomFilter.contains(1));
        log.info("是否包含-1：{}", bloomFilter.contains(-1));
        log.info("是否包含10000：{}", bloomFilter.contains(10000));
        log.info("是否包含10000000：{}", bloomFilter.contains(10000000));
    }

    @Test
    public void testObject() {
        final String key = "bloom:filter:data:v2";

        RBloomFilter<BloomDTO> bloomFilter = redissonClient.getBloomFilter(key);
        bloomFilter.tryInit(1000, 0.01);
        BloomDTO dto1 = new BloomDTO(1, "1");
        BloomDTO dto2 = new BloomDTO(10, "10");
        BloomDTO dto3 = new BloomDTO(100, "100");
        BloomDTO dto4 = new BloomDTO(1000, "1000");
        BloomDTO dto5 = new BloomDTO(10000, "10000");
        bloomFilter.add(dto1);
        bloomFilter.add(dto2);
        bloomFilter.add(dto3);
        bloomFilter.add(dto4);
        bloomFilter.add(dto5);

        log.info("是否包含(1, \"1\")：{}", bloomFilter.contains(new BloomDTO(1, "1")));
        log.info("是否包含(100, \"2\")：{}", bloomFilter.contains(new BloomDTO(100, "2")));
        log.info("是否包含(1000, \"3\")：{}", bloomFilter.contains(new BloomDTO(1000, "3")));
        log.info("是否包含(10000, \"10000\")：{}", bloomFilter.contains(new BloomDTO(10000, "10000")));
    }

    @Test
    public void testTopic() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUserId(90001);
        dto.setUsername("a-xiu-luo");
        dto.setPassword("123456");
        userLoginPublisher.sendMsg(dto);
    }
}
