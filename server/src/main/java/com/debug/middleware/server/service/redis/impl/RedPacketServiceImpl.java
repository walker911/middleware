package com.debug.middleware.server.service.redis.impl;

import com.debug.middleware.server.common.RedisKeyPrefixConstant;
import com.debug.middleware.server.dto.RedPacketDTO;
import com.debug.middleware.server.service.redis.RedPacketService;
import com.debug.middleware.server.service.redis.RedService;
import com.debug.middleware.server.util.RedPacketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/24
 */
@Slf4j
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
            // leftPushAll problem
            redisTemplate.opsForList().leftPushAll(redId, amounts.toArray());

            String redTotalKey = redId + ":total";
            redisTemplate.opsForValue().set(redTotalKey, dto.getTotal());

            redService.recordRedPacket(dto, redId, amounts);

            return redId;
        } else {
            throw new RuntimeException("系统异常-分发红包-参数不合法！");
        }
    }

    @Override
    public BigDecimal rob(Long userId, String redId) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        // 判断是否抢过红包
        Object obj = valueOperations.get(redId + userId + ":rob");
        if (obj != null) {
            return new BigDecimal(obj.toString());
        }

        // 点红包
        boolean res = click(redId);

        // 拆红包
        if (res) {
            Object value = redisTemplate.opsForList().rightPop(redId);
            if (value != null) {
                String redTotalKey = redId + ":total";
                Integer currTotal = valueOperations.get(redTotalKey) != null ? (Integer) valueOperations.get(redTotalKey) : 0;
                valueOperations.set(redTotalKey, currTotal - 1);

                BigDecimal result = new BigDecimal(value.toString()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                redService.recordRobRedPacket(userId, redId, new BigDecimal(value.toString()));
                valueOperations.set(redId + userId + ":rob", result, 24L, TimeUnit.HOURS);
                log.info("当前用户抢到红包了：userId={} key={} 金额={}", userId, redId, result);

                return result;
            }
        }

        return null;
    }

    private boolean click(String redId) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

        String redTotalKey = redId + ":total";
        Object total = valueOperations.get(redTotalKey);
        if (total != null && Integer.parseInt(total.toString()) > 0) {
            return true;
        }

        return false;
    }
}
