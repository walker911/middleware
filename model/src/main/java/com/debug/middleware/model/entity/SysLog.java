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
 * @date 2020/8/26
 */
@Getter
@Setter
public class SysLog {

    private Integer id;

    private Integer userId;

    private String module;

    private String data;

    private String memo;

    private LocalDateTime createTime;
}
