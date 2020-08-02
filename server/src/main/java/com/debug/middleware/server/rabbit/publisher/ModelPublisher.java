package com.debug.middleware.server.rabbit.publisher;

import com.debug.middleware.server.entity.EventInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author walker
 * @date 2020/8/1
 */
@Slf4j
@Component
public class ModelPublisher {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final Environment env;

    public ModelPublisher(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, Environment env) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.env = env;
    }

    /**
     * 发送消息 - fanoutExchange
     *
     * @param info
     */
    public void sendMsg(EventInfo info) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.fanout.exchange.name"));

            rabbitTemplate.convertAndSend(info);
            log.info("消息模型fanoutExchange-生产者-发送消息：{}", info);
        } catch (Exception e) {
            log.error("消息模型fanoutExchange-生产者-发送消息发生异常：{}", info, e.fillInStackTrace());
        }
    }

    /**
     * 发送消息 - directExchange one
     *
     * @param info
     */
    public void sendMsgDirectOne(EventInfo info) {
        if (info != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.one.name"));
                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(info)).build();
                rabbitTemplate.convertAndSend(msg);
                log.info("消息模型DirectExchange-one-生产者-发送消息：{}", info);
            } catch (Exception e) {
                log.error("消息模型DirectExchange-one-生产者-发送消息发送异常：{}", info, e.fillInStackTrace());
            }
        }
    }

    /**
     * 发送消息 - directExchange
     *
     * @param info
     */
    public void sendMsgDirectTwo(EventInfo info) {
        if (info != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.direct.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.direct.routing.key.two.name"));
                rabbitTemplate.convertAndSend(info);
                log.info("消息模型DirectExchange-two-生产者-发送消息：{}", info);
            } catch (Exception e) {
                log.error("消息模型DirectExchange-two-生产者-发送消息发送异常：{}", info, e.fillInStackTrace());
            }
        }
    }
}
