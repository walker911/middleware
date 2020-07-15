package com.debug.middleware.server.common;

import lombok.Getter;
import lombok.Setter;

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
public class ResultBean<T> {

    private int code;

    private String msg;

    private T data;

    private static final Integer SUCCESS = 0;

    private static final String SUCCESS_MSG = "成功";

    public ResultBean() {
    }

    public ResultBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<>(SUCCESS, SUCCESS_MSG, data);
    }

}
