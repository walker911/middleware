package com.debug.middleware.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/15
 */
@Getter
@Setter
public class Item {

    private Long id;

    private String code;

    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
