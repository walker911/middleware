package com.debug.middleware.server.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * Spring事件驱动模型-生产者
 * </p>
 *
 * @author mu qin
 * @date 2020/7/27
 */
@Slf4j
@Component
public class Publisher {

    /**
     * 发送消息组件
     */
    private final ApplicationEventPublisher publisher;

    public Publisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * 发送消息
     */
    public void sendMsg() {
        LoginEvent event = new LoginEvent(this, "debug",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), "127.0.0.1");
        publisher.publishEvent(event);
        log.info("Spring事件驱动模型-发送消息：{}", event);
    }
}
