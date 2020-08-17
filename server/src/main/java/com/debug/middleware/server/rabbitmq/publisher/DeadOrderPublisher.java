package com.debug.middleware.server.rabbitmq.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 死信队列-生产者-用户下单支付超时消息模型
 *
 * @author walker
 * @date 2020/8/17
 */
@Slf4j
@Component
public class DeadOrderPublisher {

    private final Environment env;
    private final RabbitTemplate rabbitTemplate;

    public DeadOrderPublisher(Environment env, RabbitTemplate rabbitTemplate) {
        this.env = env;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 将用户下单记录id当作消息发给死信队列
     *
     * @param orderId
     */
    public void sendMsg(Integer orderId) {
        try {
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.producer.order.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("mq.producer.order.routing.key.name"));
            rabbitTemplate.convertAndSend(orderId, message -> {
                MessageProperties properties = message.getMessageProperties();
                properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Integer.class);
                return message;
            });
            log.info("用户下单支付超时-发送用户下单记录id的消息入死信队列-内容为：orderId={}", orderId);
        } catch (Exception e) {
            log.error("用户下单支付超时-发送用户下单记录id的消息入死信队列-发送异常：orderId={}", orderId, e.fillInStackTrace());
        }
    }
}
