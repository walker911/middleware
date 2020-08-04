package com.debug.middleware.server.rabbitmq.consumer;

import com.debug.middleware.server.rabbitmq.entity.KnowledgeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 确认消费模式-手动确认消费-消费者
 *
 * @author walker
 * @date 2020/8/2
 */
@Slf4j
@Component
public class KnowledgeManualConsumer {

    private final ObjectMapper objectMapper;

    public KnowledgeManualConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${mq.manual.knowledge.queue.name}", containerFactory = "singleListenerContainerManual")
    public void consumerMsg(Message message, Channel channel) throws IOException {
        MessageProperties properties = message.getMessageProperties();
        long deliveryTag = properties.getDeliveryTag();
        try {
            KnowledgeInfo info = objectMapper.readValue(message.getBody(), KnowledgeInfo.class);
            log.info("确认消费模式-人为手动确认消费-监听器监听消费消息-内容为：{}", info);
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            log.error("确认消费模式-人为手动确认消费-监听器监听消费消息-发生异常：", e.fillInStackTrace());
            channel.basicReject(deliveryTag, false);
        }
    }
}
