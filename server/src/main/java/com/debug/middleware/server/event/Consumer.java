package com.debug.middleware.server.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Spring 事件驱动模型 - 消费者
 * </p>
 *
 * @author mu qin
 * @date 2020/7/27
 */
@Slf4j
@Component
public class Consumer implements ApplicationListener<LoginEvent> {

    /**
     * 监听消费消息
     *
     * @param event 登录消息
     */
    @Async
    @Override
    public void onApplicationEvent(LoginEvent event) {
        log.info("Spring 事件驱动模型-接收消息：{}", event);
        // TODO: 后续实现自身业务逻辑
    }
}
