package com.debug.middleware.model.entity;

import java.time.LocalDateTime;

/**
 * @author walker
 * @date 2020/8/16
 */
public class MqOrder {

    private Integer id;

    private Integer orderId;

    private LocalDateTime businessTime;

    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(LocalDateTime businessTime) {
        this.businessTime = businessTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
