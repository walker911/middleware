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
public class RedDetail {

    private Long id;

    private Long recordId;

    private BigDecimal amount;

    private Integer isActive;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
