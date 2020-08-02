package com.debug.middleware.server.rabbitmq.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 确认消费实体对象信息
 *
 * @author walker
 * @date 2020/8/2
 */
@Getter
@Setter
@ToString
public class KnowledgeInfo implements Serializable {

    private Integer id;

    private String mode;

    private String code;
}
