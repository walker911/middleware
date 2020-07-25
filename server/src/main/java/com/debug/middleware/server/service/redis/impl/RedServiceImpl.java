package com.debug.middleware.server.service.redis.impl;

import com.debug.middleware.model.entity.RedDetail;
import com.debug.middleware.model.entity.RedRecord;
import com.debug.middleware.model.entity.RedRobRecord;
import com.debug.middleware.model.mapper.RedDetailMapper;
import com.debug.middleware.model.mapper.RedRecordMapper;
import com.debug.middleware.model.mapper.RedRobRecordMapper;
import com.debug.middleware.server.dto.RedPacketDTO;
import com.debug.middleware.server.service.redis.RedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/24
 */
@Service
public class RedServiceImpl implements RedService {

    @Autowired
    private RedRecordMapper redRecordMapper;
    @Autowired
    private RedDetailMapper redDetailMapper;
    @Autowired
    private RedRobRecordMapper redRobRecordMapper;

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordRedPacket(RedPacketDTO dto, String redId, List<Integer> amounts) {
        RedRecord redRecord = new RedRecord();
        redRecord.setUserId(dto.getUserId());
        redRecord.setAmount(BigDecimal.valueOf(dto.getAmount()));
        redRecord.setTotal(dto.getTotal());
        redRecord.setRedPacket(redId);
        redRecord.setCreateTime(LocalDateTime.now());
        redRecordMapper.save(redRecord);

        RedDetail detail;
        for (Integer amount : amounts) {
            detail = new RedDetail();
            detail.setRecordId(redRecord.getId());
            detail.setAmount(BigDecimal.valueOf(amount));
            detail.setCreateTime(LocalDateTime.now());
            redDetailMapper.save(detail);
        }
    }

    @Async
    @Override
    public void recordRobRedPacket(Long userId, String redId, BigDecimal amount) {
        RedRobRecord robRecord = new RedRobRecord();
        robRecord.setUserId(userId);
        robRecord.setRedPacket(redId);
        robRecord.setAmount(amount);
        robRecord.setRobTime(LocalDateTime.now());
        robRecord.setCreateTime(LocalDateTime.now());

        redRobRecordMapper.save(robRecord);
    }
}
