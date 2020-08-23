package com.debug.middleware.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author walker
 * @date 2020/8/23
 */
@Getter
@Setter
@ToString
public class UserAccount {

    private Integer id;

    private Integer userId;

    private BigDecimal amount;

    private Integer version;

    private Integer isActive;
}
