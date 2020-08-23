package com.debug.middleware.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author walker
 * @date 2020/8/23
 */
@Getter
@Setter
@ToString
public class UserAccountRecord {

    private Integer id;

    private Integer accountId;

    private BigDecimal money;

    private LocalDateTime createTime;

}
