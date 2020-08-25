package com.debug.middleware.server.controller.lock;

import com.debug.middleware.api.BaseResponse;
import com.debug.middleware.api.enums.StatusCode;
import com.debug.middleware.server.dto.UserRegDTO;
import com.debug.middleware.server.service.lock.UserRegService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/25
 */
@Slf4j
@RestController
public class UserRegController {

    private final UserRegService userRegService;

    public UserRegController(UserRegService userRegService) {
        this.userRegService = userRegService;
    }

    @PostMapping(value = "/user/reg/submit")
    public BaseResponse<String> reg(UserRegDTO dto) {
        if (StringUtils.isBlank(dto.getUsername()) || StringUtils.isBlank(dto.getPassword())) {
            return new BaseResponse<>(StatusCode.INVALID_PARAMS);
        }

        BaseResponse<String> response = new BaseResponse<>(StatusCode.SUCCESS);
        try {
            userRegService.userRegNoLock(dto);
            // userRegService.userRegWithLock(dto);
        } catch (Exception e) {
            log.error("用户注册异常", e);
            response = new BaseResponse<>(StatusCode.FAIL.getCode(), e.getMessage());
        }

        return response;
    }
}
