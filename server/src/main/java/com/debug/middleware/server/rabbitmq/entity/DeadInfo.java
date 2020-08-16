package com.debug.middleware.server.rabbitmq.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author walker
 * @date 2020/8/16
 */
@Getter
@Setter
@ToString
public class DeadInfo implements Serializable {

    private static final long serialVersionUID = -7648368917323230940L;

    private Integer id;

    private String msg;

    public DeadInfo() {
    }

    public DeadInfo(Integer id, String msg) {
        this.id = id;
        this.msg = msg;
    }
}
