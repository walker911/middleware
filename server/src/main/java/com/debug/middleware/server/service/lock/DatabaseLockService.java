package com.debug.middleware.server.service.lock;

import com.debug.middleware.server.dto.UserAccountDTO;

/**
 * 基于数据库级别的乐观悲观锁服务
 *
 * @author walker
 * @date 2020/8/23
 */
public interface DatabaseLockService {

    void takeMoney(UserAccountDTO dto);

    void takeMoneyWithLock(UserAccountDTO dto);

    void takeMoneyWithLockNegative(UserAccountDTO dto);
}
