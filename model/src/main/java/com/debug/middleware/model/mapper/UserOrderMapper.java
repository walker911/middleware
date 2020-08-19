package com.debug.middleware.model.mapper;

import com.debug.middleware.model.entity.UserOrder;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 更新
     *
     * @param order
     * @return
     */
    int update(UserOrder order);

    UserOrder selectByIdAndStatus(@Param("orderId") Integer orderId, @Param("status") Integer status);
}
