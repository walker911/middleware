package com.debug.middleware.server.rabbitmq.consumer;

import com.debug.middleware.server.rabbitmq.entity.KnowledgeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author walker
 * @date 2020/8/2
 */
@Slf4j
@Component
public class KnowledgeConsumer {

    private final ObjectMapper objectMapper;

    public KnowledgeConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${mq.auto.knowledge.queue.name}", containerFactory = "singleListenerContainerAuto")
    public void consumerAutoMsg(Message message) {
        try {
            KnowledgeInfo info = objectMapper.readValue(message.getBody(), KnowledgeInfo.class);
            log.info("基于AUTO的确认消息模式-消费者监听消费消息-内容为：{}", info);
        } catch (IOException e) {
            log.error("基于AUTO的确认消息模式-消费者监听消费消息-发送异常：", e.fillInStackTrace());
        }
    }
}
