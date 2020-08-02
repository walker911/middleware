package com.debug.middleware.server.rabbit.consumer;

import com.debug.middleware.server.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 基本消息模型 - 消费者
 *
 * @author walker
 * @date 2020/8/1
 */
@Slf4j
@Component
public class BasicConsumer {

    private final ObjectMapper objectMapper;

    public BasicConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 监听并接收消费队列中消息 - 采用单一容器工厂实例
     *
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${mq.basic.info.queue.name}", durable = "true"),
            exchange = @Exchange(value = "${mq.basic.info.exchange.name}"),
            key = {"${mq.basic.info.routing.key.name}"}), containerFactory = "singleListenerContainer")
    public void consumerMsg(Message msg) {
        try {
            String message = new String(msg.getBody(), StandardCharsets.UTF_8);
            log.info("基本消息模型-消费者-监听消费到消息：{}", message);
        } catch (Exception e) {
            log.error("基本消息模型-消费者-发生异常：", e.fillInStackTrace());
        }
    }

    /**
     * 监听消费处理对象信息
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "${mq.object.info.queue.name}", durable = "true"),
            exchange = @Exchange(value = "${mq.object.info.exchange.name}"),
            key = {"${mq.object.info.routing.key.name}"}), containerFactory = "singleListenerContainer")
    public void consumerObjectMsg(Message message) {
        try {
            Person person = objectMapper.readValue(message.getBody(), Person.class);
            log.info("基本消息模型-监听消费处理对象信息-消费者-监听消费到消息：{}", person);
        } catch (IOException e) {
            log.info("基本消息模型-监听消费处理对象信息-消费者-发送异常：", e.fillInStackTrace());
        }
    }
}
