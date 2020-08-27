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
 * @date 2020/8/27
 */
@Getter
@Setter
@ToString
public class PraiseDTO implements Serializable {

    private static final long serialVersionUID = 1153893828795677657L;

    private Integer blogId;

    private Integer userId;
}
