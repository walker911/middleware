package com.debug.middleware.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/8
 */
@SpringBootApplication
@MapperScan(basePackages = "com.debug.middleware.model")
public class MiddlewareServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiddlewareServerApplication.class, args);
    }
}
