package com.debug.middleware.server.service.redis;

import com.debug.middleware.server.dto.RedPacketDTO;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/24
 */
public interface RedPacketService {

    String handOut(RedPacketDTO dto);

    BigDecimal rob(Long userId, String redId);

}
