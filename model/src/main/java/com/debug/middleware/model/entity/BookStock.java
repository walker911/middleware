package com.debug.middleware.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author walker
 * @date 2020/9/6
 */
@Getter
@Setter
public class BookStock {

    private Integer id;

    private String bookNo;

    private Integer stock;

    private Integer isActive;
}
