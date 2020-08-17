package com.debug.middleware.server.service.impl;

import com.debug.middleware.model.entity.MqOrder;
import com.debug.middleware.model.entity.UserOrder;
import com.debug.middleware.model.mapper.MqOrderMapper;
import com.debug.middleware.model.mapper.UserOrderMapper;
import com.debug.middleware.server.dto.UserOrderDTO;
import com.debug.middleware.server.rabbitmq.publisher.DeadOrderPublisher;
import com.debug.middleware.server.service.DeadUserOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author walker
 * @date 2020/8/16
 */
@Slf4j
@Service
public class DeadUserOrderServiceImpl implements DeadUserOrderService {

    private final UserOrderMapper userOrderMapper;
    private final MqOrderMapper mqOrderMapper;
    private final DeadOrderPublisher deadOrderPublisher;

    public DeadUserOrderServiceImpl(UserOrderMapper userOrderMapper, MqOrderMapper mqOrderMapper, DeadOrderPublisher deadOrderPublisher) {
        this.userOrderMapper = userOrderMapper;
        this.mqOrderMapper = mqOrderMapper;
        this.deadOrderPublisher = deadOrderPublisher;
    }

    @Override
    public void pushUserOrder(UserOrderDTO dto) {
        UserOrder userOrder = new UserOrder();
        BeanUtils.copyProperties(dto, userOrder);
        userOrder.setStatus(1);
        userOrder.setCreateTime(LocalDateTime.now());
        userOrderMapper.save(userOrder);
        log.info("用户下单成功，下单信息为：{}", userOrder);
        // 放入死信队列
        Integer id = userOrder.getId();
        deadOrderPublisher.sendMsg(id);
    }

    /**
     * 更新用户下单记录的状态
     *
     * @param userOrder
     */
    @Override
    public void updateUserOrderRecord(UserOrder userOrder) {
        try {
            if (userOrder != null) {
                userOrder.setIsActive(0);
                userOrder.setUpdateTime(LocalDateTime.now());
                userOrderMapper.update(userOrder);

                // 记录失效下单记录
                MqOrder mqOrder = new MqOrder();
                mqOrder.setOrderId(userOrder.getId());
                mqOrder.setBusinessTime(LocalDateTime.now());
                mqOrder.setMemo("更新失效用户下单记录orderId=" + userOrder.getId());
                mqOrderMapper.save(mqOrder);
            }
        } catch (Exception e) {
            log.error("用户下单支付超时-处理服务-更新用户下单记录的状态发生异常：", e.fillInStackTrace());
        }
    }


}
