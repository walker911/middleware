package com.debug.middleware.server.rabbit.consumer;

import com.debug.middleware.server.entity.EventInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author walker
 * @date 2020/8/1
 */
@Slf4j
@Component
public class ModelConsumer {

    private final ObjectMapper objectMapper;

    public ModelConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 监听并消费队列中的消息
     *
     * @param msg
     */
    @RabbitListener(queues = "${mq.fanout.queue.one.name}", containerFactory = "singleListenerContainer")
    public void consumerFanoutMsgOne(Message msg) {
        try {
            EventInfo info = objectMapper.readValue(msg.getBody(), EventInfo.class);
            log.info("消息模型fanoutExchange-one-消费者-监听消费到消息：{}", info);
        } catch (IOException e) {
            log.error("消息模型-消费者-发生异常：", e.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${mq.fanout.queue.two.name}", containerFactory = "singleListenerContainer")
    public void consumerFanoutMsgTwo(Message msg) {
        try {
            EventInfo info = objectMapper.readValue(msg.getBody(), EventInfo.class);
            log.info("消息模型fanoutExchange-two-消费者-监听消费到消息：{}", info);
        } catch (IOException e) {
            log.error("消息模型-消费者-发生异常：", e.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${mq.direct.queue.one.name}", containerFactory = "singleListenerContainer")
    public void consumerDirectMsgOne(Message message) {
        try {
            EventInfo info = objectMapper.readValue(message.getBody(), EventInfo.class);
            log.info("消息模型directExchange-one-消费者-监听消费到消息：{}", info);
        } catch (IOException e) {
            log.error("消息模型directExchange-one-消费者-监听消费发送异常：", e.fillInStackTrace());
        }
    }

    @RabbitListener(queues = "${mq.direct.queue.two.name}", containerFactory = "singleListenerContainer")
    public void consumerDirectMsgTwo(Message message) {
        try {
            EventInfo info = objectMapper.readValue(message.getBody(), EventInfo.class);
            log.info("消息模型directExchange-two-消费者-监听消费到消息：{}", info);
        } catch (IOException e) {
            log.error("消息模型directExchange-two-消费者-监听消费发送异常：", e.fillInStackTrace());
        }
    }
}
