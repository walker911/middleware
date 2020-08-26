package com.debug.middleware.server.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * <p>
 * Redisson 配置
 * </p>
 *
 * @author mu qin
 * @date 2020/8/26
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redisson() throws IOException {
        return Redisson.create();
    }
}
