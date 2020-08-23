package com.debug.middleware.server.service.lock.impl;

import com.debug.middleware.model.entity.UserAccount;
import com.debug.middleware.model.entity.UserAccountRecord;
import com.debug.middleware.model.mapper.UserAccountMapper;
import com.debug.middleware.model.mapper.UserAccountRecordMapper;
import com.debug.middleware.server.dto.UserAccountDTO;
import com.debug.middleware.server.service.lock.DatabaseLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 基于数据库级别的乐观悲观锁服务
 *
 * @author walker
 * @date 2020/8/23
 */
@Slf4j
@Service
public class DatabaseLockServiceImpl implements DatabaseLockService {

    private final UserAccountMapper accountMapper;
    private final UserAccountRecordMapper accountRecordMapper;

    public DatabaseLockServiceImpl(UserAccountMapper accountMapper, UserAccountRecordMapper accountRecordMapper) {
        this.accountMapper = accountMapper;
        this.accountRecordMapper = accountRecordMapper;
    }

    /**
     * 用户账户提取金额
     *
     * @param dto
     */
    @Override
    @Transactional
    public void takeMoney(UserAccountDTO dto) {
        UserAccount account = accountMapper.selectByUserId(dto.getUserId());
        if (account != null && account.getAmount().doubleValue() - dto.getAmount() >= 0) {
            // 更新余额
            accountMapper.updateAmount(dto.getAmount(), account.getId());

            // 保存提现记录
            saveAccountRecord(account.getId(), dto.getAmount());

            log.info("当前待提现的金额为：{} 用户账户余额为：{}", dto.getAmount(), account.getAmount());
        } else {
            throw new RuntimeException("账户不存在或账户余额不足");
        }
    }

    /**
     * 用户提取金额 - 乐观锁
     *
     * @param dto
     */
    @Override
    @Transactional
    public void takeMoneyWithLock(UserAccountDTO dto) {
        UserAccount account = accountMapper.selectByUserId(dto.getUserId());
        if (account != null && account.getAmount().doubleValue() - dto.getAmount() >= 0) {
            // 更新余额 - version机制
            int res = accountMapper.updateAmountByVersion(dto.getAmount(), account.getId(), account.getVersion());

            if (res > 0) {
                // 保存提现记录
                saveAccountRecord(account.getId(), dto.getAmount());
                log.info("当前待提现的金额为：{} 用户账户余额为：{}", dto.getAmount(), account.getAmount());
            }
        } else {
            throw new RuntimeException("账户不存在或账户余额不足");
        }
    }

    /**
     * 用户提现 - 悲观锁
     *
     * @param dto
     */
    @Override
    @Transactional
    public void takeMoneyWithLockNegative(UserAccountDTO dto) {
        UserAccount account = accountMapper.selectByUserIdLock(dto.getUserId());
        if (account != null && account.getAmount().doubleValue() - dto.getAmount() >= 0) {
            // 更新余额
            int res = accountMapper.updateAmountLock(dto.getAmount(), account.getId());

            if (res > 0) {
                // 保存提现记录
                saveAccountRecord(account.getId(), dto.getAmount());
                log.info("当前待提现的金额为：{} 用户账户余额为：{}", dto.getAmount(), account.getAmount());
            }
        } else {
            throw new RuntimeException("账户不存在或账户余额不足");
        }
    }

    private void saveAccountRecord(Integer accountId, Double amount) {
        UserAccountRecord record = new UserAccountRecord();
        record.setAccountId(accountId);
        record.setMoney(BigDecimal.valueOf(amount));
        record.setCreateTime(LocalDateTime.now());
        accountRecordMapper.save(record);
    }
}
