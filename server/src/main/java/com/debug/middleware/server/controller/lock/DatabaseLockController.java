package com.debug.middleware.server.controller.lock;

import com.debug.middleware.api.BaseResponse;
import com.debug.middleware.api.enums.StatusCode;
import com.debug.middleware.server.dto.UserAccountDTO;
import com.debug.middleware.server.service.lock.DatabaseLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基于数据库的乐观悲观锁
 *
 * @author walker
 * @date 2020/8/23
 */
@Slf4j
@RestController
public class DatabaseLockController {

    private final DatabaseLockService databaseLockService;

    public DatabaseLockController(DatabaseLockService databaseLockService) {
        this.databaseLockService = databaseLockService;
    }

    @PostMapping(value = "/db/money/take")
    public BaseResponse<String> takeMoney(@RequestBody UserAccountDTO dto) {
        // 校验
        if (dto.getAmount() == null || dto.getUserId() == null) {
            return new BaseResponse<>(StatusCode.INVALID_PARAMS);
        }

        BaseResponse<String> response = new BaseResponse<>(StatusCode.SUCCESS);
        try {
            // 不加锁
            // databaseLockService.takeMoney(dto);
            // 加锁 - 乐观
            // databaseLockService.takeMoneyWithLock(dto);
            // 加锁 - 悲观
            databaseLockService.takeMoneyWithLockNegative(dto);
        } catch (Exception e) {
            response = new BaseResponse<>(StatusCode.FAIL.getCode(), e.getMessage());
        }

        return response;
    }
}
