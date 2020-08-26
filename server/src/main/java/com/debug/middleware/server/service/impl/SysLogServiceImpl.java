package com.debug.middleware.server.service.impl;

import com.debug.middleware.model.entity.SysLog;
import com.debug.middleware.model.mapper.SysLogMapper;
import com.debug.middleware.server.dto.UserLoginDTO;
import com.debug.middleware.server.service.SysLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/8/26
 */
@Slf4j
@Service
public class SysLogServiceImpl implements SysLogService {

    private final SysLogMapper sysLogMapper;
    private final ObjectMapper objectMapper;

    public SysLogServiceImpl(SysLogMapper sysLogMapper, ObjectMapper objectMapper) {
        this.sysLogMapper = sysLogMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void recordLog(UserLoginDTO dto) {
        try {
            SysLog entity = new SysLog();
            entity.setUserId(dto.getUserId());
            entity.setModule("用户登录模块");
            entity.setData(objectMapper.writeValueAsString(dto));
            entity.setMemo("用户登录成功记录相关信息");
            entity.setCreateTime(LocalDateTime.now());

            sysLogMapper.save(entity);
        } catch (Exception e) {
            log.error("系统日志服务-记录用户登录成功的信息-发生异常：{}", dto, e.fillInStackTrace());
        }
    }
}
