package com.debug.middleware.server.service.impl;

import com.debug.middleware.model.entity.UserOrder;
import com.debug.middleware.model.mapper.MqOrderMapper;
import com.debug.middleware.model.mapper.UserOrderMapper;
import com.debug.middleware.server.dto.UserOrderDTO;
import com.debug.middleware.server.service.DeadUserOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author walker
 * @date 2020/8/16
 */
@Slf4j
@Service
public class DeadUserOrderServiceImpl implements DeadUserOrderService {

    private final UserOrderMapper userOrderMapper;
    private final MqOrderMapper mqOrderMapper;

    public DeadUserOrderServiceImpl(UserOrderMapper userOrderMapper, MqOrderMapper mqOrderMapper) {
        this.userOrderMapper = userOrderMapper;
        this.mqOrderMapper = mqOrderMapper;
    }

    @Override
    public void pushUserOrder(UserOrderDTO dto) {
        UserOrder userOrder = new UserOrder();
        BeanUtils.copyProperties(dto, userOrder);
        userOrder.setStatus(1);
        userOrder.setCreateTime(LocalDateTime.now());
        userOrderMapper.save(userOrder);
        log.info("用户下单成功，下单信息为：{}", userOrder);
//        Integer id = userOrder.getId();

    }


}
