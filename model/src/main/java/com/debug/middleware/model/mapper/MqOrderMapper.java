package com.debug.middleware.model.mapper;

import com.debug.middleware.model.entity.MqOrder;

/**
 * @author walker
 * @date 2020/8/16
 */
public interface MqOrderMapper {

    /**
     * 保存
     *
     * @param order
     * @return
     */
    int save(MqOrder order);
}
