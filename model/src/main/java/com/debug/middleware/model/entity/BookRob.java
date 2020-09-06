package com.debug.middleware.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author walker
 * @date 2020/9/6
 */
@Getter
@Setter
public class BookRob {

    private Integer id;

    private Integer userId;

    private String bookNo;

    private LocalDateTime robTime;
}
