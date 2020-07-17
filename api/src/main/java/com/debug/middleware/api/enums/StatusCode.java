package com.debug.middleware.api.enums;

import lombok.Getter;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/16
 */
@Getter
public enum StatusCode {

    SUCCESS(0, "成功"),
    FAIL(-1, "失败"),
    INVALID_PARAMS(201, "非法的参数"),
    INVALID_GRANT_TYPE(202, "非法的授权类型")
    ;

    private final Integer code;

    private final String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
