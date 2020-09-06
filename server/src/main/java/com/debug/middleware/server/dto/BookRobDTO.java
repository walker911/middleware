package com.debug.middleware.server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author walker
 * @date 2020/9/6
 */
@Getter
@Setter
@ToString
public class BookRobDTO implements Serializable {

    private static final long serialVersionUID = 9184390202565932270L;

    private Integer userId;

    private String bookNo;
}
