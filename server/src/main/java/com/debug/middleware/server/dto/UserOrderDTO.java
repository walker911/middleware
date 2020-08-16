package com.debug.middleware.server.dto;

/**
 * @author walker
 * @date 2020/8/16
 */
public class UserOrderDTO {

    private String orderNo;

    private Integer userId;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
