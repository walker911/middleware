package com.debug.middleware.server.rabbitmq.consumer;

import com.debug.middleware.model.entity.UserOrder;
import com.debug.middleware.model.mapper.UserOrderMapper;
import com.debug.middleware.server.service.DeadUserOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 死信队列 - 真正的队列消费者 - 用户下单支付超时消息模型
 *
 * @author walker
 * @date 2020/8/17
 */
@Slf4j
@Component
public class DeadOrderConsumer {

    private final UserOrderMapper userOrderMapper;
    private final DeadUserOrderService deadUserOrderService;

    public DeadOrderConsumer(UserOrderMapper userOrderMapper, DeadUserOrderService deadUserOrderService) {
        this.userOrderMapper = userOrderMapper;
        this.deadUserOrderService = deadUserOrderService;
    }

    /**
     * 用户下单支付超时-监听真正的队列
     */
    @RabbitListener(queues = "${mq.consumer.order.real.queue.name}", containerFactory = "singleListenerContainer")
    public void consumerMsg(@Payload Integer orderId) {
        log.info("用户下单支付超时消息模型-监听真正的队列-监听到消息内容为：orderId={}", orderId);
        UserOrder userOrder = userOrderMapper.selectByIdAndStatus(orderId, 1);
        if (userOrder != null) {
            // 表示该用户下单记录仍为“已保存”状态，已经超时，失效该记录
            deadUserOrderService.updateUserOrderRecord(userOrder);
        }
    }
}
