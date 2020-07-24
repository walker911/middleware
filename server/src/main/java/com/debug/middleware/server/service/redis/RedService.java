package com.debug.middleware.server.service.redis;

import com.debug.middleware.server.dto.RedPacketDTO;

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
public interface RedService {

    void recordRedPacket(RedPacketDTO dto, String redId, List<Integer> amounts);

    void recordRobRedPacket(Integer userId, String redId, BigDecimal amount);

}
