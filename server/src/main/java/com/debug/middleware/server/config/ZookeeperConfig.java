package com.debug.middleware.server.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/9/7
 */
@Configuration
public class ZookeeperConfig {

    @Value("${zk.host}")
    private String host;
    @Value("${zk.namespace}")
    private String namespace;

    @Bean
    public CuratorFramework curatorFramework() {
        // 采用工厂模式创建 CuratorFramework 实例，重试策略：5次，每次间隔1s
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(host)
                .namespace(namespace)
                .retryPolicy(new RetryNTimes(5, 1000))
                .build();
        curatorFramework.start();
        return curatorFramework;
    }
}
