package com.debug.middleware.api;

import com.debug.middleware.api.enums.StatusCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/16
 */
@Getter
@Setter
public class BaseResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

}
