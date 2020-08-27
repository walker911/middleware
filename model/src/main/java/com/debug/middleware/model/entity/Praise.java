package com.debug.middleware.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/27
 */
@Getter
@Setter
@ToString
public class Praise {

    private Integer id;

    private Integer blogId;

    private Integer userId;

    private LocalDateTime praiseTime;

    private Integer status;

    private Integer isActive;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
