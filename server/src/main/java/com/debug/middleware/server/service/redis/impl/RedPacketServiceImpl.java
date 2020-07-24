package com.debug.middleware.server.service.redis.impl;

import com.debug.middleware.server.common.RedisKeyPrefixConstant;
import com.debug.middleware.server.dto.RedPacketDTO;
import com.debug.middleware.server.service.redis.RedPacketService;
import com.debug.middleware.server.service.redis.RedService;
import com.debug.middleware.server.util.RedPacketUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class RedPacketServiceImpl implements RedPacketService {

    private final RedService redService;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedPacketServiceImpl(RedService redService, RedisTemplate<String, Object> redisTemplate) {
        this.redService = redService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String handOut(RedPacketDTO dto) {
        if (dto.getTotal() > 0 && dto.getAmount() > 0) {
            List<Integer> amounts = RedPacketUtil.divideRedPacket(dto.getAmount(), dto.getTotal());
            String timestamp = String.valueOf(System.nanoTime());
            String redId = new StringBuffer(RedisKeyPrefixConstant.RED_PACKET_PREFIX).append(dto.getUserId())
                    .append(":").append(timestamp).toString();
            redisTemplate.opsForList().leftPushAll(redId, amounts);

            String redTotalKey = redId + ":total";
            redisTemplate.opsForValue().set(redTotalKey, dto.getTotal());

            redService.recordRedPacket(dto, redId, amounts);

            return redId;
        } else {
            throw new RuntimeException("系统异常-分发红包-参数不合法！");
        }
    }

    @Override
    public BigDecimal rob(Integer userId, String redId) {
        return null;
    }
}
