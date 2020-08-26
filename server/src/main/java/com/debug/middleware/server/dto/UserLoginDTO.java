package com.debug.middleware.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
@ToString
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 6079996753617691105L;

    private Integer userId;

    private String username;

    private String password;
}
