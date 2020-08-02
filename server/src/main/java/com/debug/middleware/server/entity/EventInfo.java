package com.debug.middleware.server.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author walker
 * @date 2020/8/1
 */
@Getter
@Setter
@ToString
public class EventInfo implements Serializable {

    private static final long serialVersionUID = 5454512990327982646L;

    private Integer id;

    private String module;

    private String name;

    private String desc;

    public EventInfo() {
    }

    public EventInfo(Integer id, String module, String name, String desc) {
        this.id = id;
        this.module = module;
        this.name = name;
        this.desc = desc;
    }
}
