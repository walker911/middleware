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
 * @date 2020/8/25
 */
@Getter
@Setter
@ToString
public class UserReg {

    private Integer id;

    private String username;

    private String password;

    private LocalDateTime createTime;
}
