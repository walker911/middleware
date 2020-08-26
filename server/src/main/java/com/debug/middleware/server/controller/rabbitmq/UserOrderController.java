package com.debug.middleware.server.controller.rabbitmq;

import com.debug.middleware.api.BaseResponse;
import com.debug.middleware.api.enums.StatusCode;
import com.debug.middleware.server.dto.UserOrderDTO;
import com.debug.middleware.server.service.DeadUserOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author walker
 * @date 2020/8/16
 */
@RestController
public class UserOrderController {

    private final DeadUserOrderService userOrderService;

    public UserOrderController(DeadUserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    /**
     * 用户下单请求的接收与处理
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "/user/order/push")
    public BaseResponse<String> order(@RequestBody UserOrderDTO dto) {
        BaseResponse<String> response = new BaseResponse<>(StatusCode.SUCCESS);
        try {
            userOrderService.pushUserOrder(dto);
        } catch (Exception e) {
            response = new BaseResponse<>(StatusCode.FAIL);
        }
        return response;
    }
}
