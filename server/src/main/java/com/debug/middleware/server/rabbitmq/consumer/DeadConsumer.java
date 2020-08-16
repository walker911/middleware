package com.debug.middleware.server.rabbitmq.consumer;

import com.debug.middleware.server.rabbitmq.entity.DeadInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 死信队列 - 真正的队列消费者
 * @author walker
 * @date 2020/8/16
 */
@Slf4j
@Component
public class DeadConsumer {

    private final ObjectMapper objectMapper;

    public DeadConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 监听真正的队列 - 消息队列中的消息 - 面向消费者
     *
     * @param message
     */
    @RabbitListener(queues = "${mq.consumer.real.queue.name}", containerFactory = "singleListenerContainer")
    public void consumerMsg(Message message) {
        try {
            DeadInfo info = objectMapper.readValue(message.getBody(), DeadInfo.class);
            log.info("死信队列-监听真正的队列-消费队列中的消息，监听到消息内容为：{}", info);
            // TODO 用于执行后续的相关业务逻辑
        } catch (Exception e) {
            log.error("死信队列-监听真正的队列-消费队列中的消息-面向消费者-发生异常：", e.fillInStackTrace());
        }
    }
}
