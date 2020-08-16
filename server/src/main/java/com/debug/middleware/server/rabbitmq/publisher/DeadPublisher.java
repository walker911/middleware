package com.debug.middleware.server.rabbitmq.publisher;

import com.debug.middleware.server.rabbitmq.entity.DeadInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 死信队列 - 生产者
 *
 * @author walker
 * @date 2020/8/16
 */
@Slf4j
@Component
public class DeadPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Environment env;

    public DeadPublisher(RabbitTemplate rabbitTemplate, Environment env) {
        this.rabbitTemplate = rabbitTemplate;
        this.env = env;
    }

    /**
     * 发送对象类型的消息给死信队列
     *
     * @param deadInfo
     */
    public void sendMsg(DeadInfo deadInfo) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.producer.basic.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("mq.producer.basic.routing.key.name"));
            rabbitTemplate.convertAndSend(deadInfo, message -> {
                MessageProperties properties = message.getMessageProperties();
                properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, DeadInfo.class);
                // 设置消息的TTL, 当消息和队列同时设置了TTL时，则取较短时间的值
                // properties.setExpiration(String.valueOf(10000));
                return message;
            });
            log.info("死信队列-发送对象类型的消息入死信队列-内容为：{}", deadInfo);
        } catch (Exception e) {
            log.error("死信队列-发送对象类型的消息入死信队列-发送异常：{}", deadInfo, e.fillInStackTrace());
        }
    }
}
