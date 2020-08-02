package com.debug.middleware.server.rabbitmq.publisher;

import com.debug.middleware.server.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 基本消息模型 - 生产者
 *
 * @author walker
 * @date 2020/8/1
 */
@Slf4j
@Component
public class BasicPublisher {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final Environment env;

    public BasicPublisher(ObjectMapper objectMapper, RabbitTemplate rabbitTemplate, Environment env) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.env = env;
    }

    /**
     * 发送消息
     *
     * @param message 待发送的消息
     */
    public void sendMsg(String message) {
        if (!Strings.isNullOrEmpty(message)) {
            try {
                // 设置消息传输格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                // 指定消息模型中的交换机
                rabbitTemplate.setExchange(env.getProperty("mq.basic.info.exchange.name"));
                // 指定路由
                rabbitTemplate.setRoutingKey(env.getProperty("mq.basic.info.routing.key.name"));

                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message))
                        .setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                rabbitTemplate.convertAndSend(msg);
                log.info("基本消息模型-生产者-发送消息：{}", message);
            } catch (Exception e) {
                log.error("基本消息模型-生产者-发送消息发生异常：{}", message, e.fillInStackTrace());
            }

        }
    }

    /**
     * 发送对象类型的消息
     *
     * @param person
     */
    public void sendObjectMsg(Person person) {
        if (person != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.object.info.exchange.name"));
                rabbitTemplate.setRoutingKey(env.getProperty("mq.object.info.routing.key.name"));
                rabbitTemplate.convertAndSend(person);
                log.info("基本消息模型-生产者-发送对象类型的消息：{}", person);
            } catch (Exception e) {
                log.info("基本消息模型-生产者-发送对象类型的消息发送异常：{}", person, e.fillInStackTrace());
            }
        }
    }
}
