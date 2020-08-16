package com.debug.middleware.model.mapper;

import com.debug.middleware.model.entity.UserOrder;

/**
 * @author walker
 * @date 2020/8/16
 */
public interface UserOrderMapper {

    /**
     * 保存
     *
     * @param order
     * @return
     */
    int save(UserOrder order);
}
