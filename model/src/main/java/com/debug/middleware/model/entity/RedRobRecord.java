package com.debug.middleware.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/16
 */
@Getter
@Setter
public class RedRobRecord {

    private Long id;

    private Long userId;

    private String redPacket;

    private BigDecimal amount;

    private LocalDateTime robTime;

    private Integer isActive;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
