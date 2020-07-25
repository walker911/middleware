package com.debug.middleware.server.controller.redis;

import com.debug.middleware.api.BaseResponse;
import com.debug.middleware.api.enums.StatusCode;
import com.debug.middleware.server.dto.RedPacketDTO;
import com.debug.middleware.server.service.redis.RedPacketService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/24
 */
@RestController
public class RedPacketController {

    private final RedPacketService redPacketService;
    private static final String PREFIX = "red/packet";

    public RedPacketController(RedPacketService redPacketService) {
        this.redPacketService = redPacketService;
    }

    @PostMapping(value = PREFIX + "/hand/out")
    public BaseResponse<String> handOut(@Validated @RequestBody RedPacketDTO dto, BindingResult result) {
        // 参数校验
        if (result.hasErrors()) {
            return new BaseResponse<>(StatusCode.INVALID_PARAMS);
        }

        BaseResponse<String> response = new BaseResponse<>(StatusCode.SUCCESS);
        String redId = redPacketService.handOut(dto);
        response.setData(redId);

        return response;
    }

    @GetMapping(value = PREFIX + "/rob")
    public BaseResponse<BigDecimal> rob(@RequestParam Long userId, @RequestParam String redId) {
        BaseResponse<BigDecimal> response = new BaseResponse<>(StatusCode.SUCCESS);

        BigDecimal result = redPacketService.rob(userId, redId);
        if (result != null) {
            response.setData(result);
        } else {
            response = new BaseResponse<>(StatusCode.FAIL.getCode(), "红包已被抢完！");
        }

        return response;
    }
}
