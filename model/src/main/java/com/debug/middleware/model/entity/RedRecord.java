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
public class RedRecord {

    private Long id;

    private Long userId;

    private String redPacket;

    private Integer total;

    private BigDecimal amount;

    private Integer isActive;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
