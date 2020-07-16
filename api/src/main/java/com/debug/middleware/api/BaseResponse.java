package com.debug.middleware.api;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/16
 */
public class BaseResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
