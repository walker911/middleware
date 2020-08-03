package com.debug.middleware.server.rabbitmq.publisher;

import com.debug.middleware.server.rabbitmq.entity.KnowledgeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author walker
 * @date 2020/8/2
 */
@Slf4j
@Component
public class KnowledgePublisher {

    private final Environment env;
    private final RabbitTemplate rabbitTemplate;

    public KnowledgePublisher(Environment env, RabbitTemplate rabbitTemplate) {
        this.env = env;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 基于Auto机制 - 生产者发送消息
     *
     * @param info
     */
    public void sendAutoMsg(KnowledgeInfo info) {
        if (info != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.auto.knowledge.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.auto.knowledge.routing.key.name"));
                rabbitTemplate.convertAndSend(info);
                log.info("基于AUTO机制-生产者发送消息-内容为：{}", info);
            } catch (Exception e) {
                log.error("基于AUTO机制-生产者发送消息-发送异常：{}", info, e.fillInStackTrace());
            }
        }
    }
}
