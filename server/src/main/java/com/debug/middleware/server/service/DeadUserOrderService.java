package com.debug.middleware.server.service;

import com.debug.middleware.model.entity.UserOrder;
import com.debug.middleware.server.dto.UserOrderDTO;

/**
 * @author walker
 * @date 2020/8/16
 */
public interface DeadUserOrderService {

    void pushUserOrder(UserOrderDTO dto);

    void updateUserOrderRecord(UserOrder userOrder);

}
