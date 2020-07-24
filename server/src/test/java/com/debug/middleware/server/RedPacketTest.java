package com.debug.middleware.server;

import com.debug.middleware.server.util.RedPacketUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/24
 */
@Slf4j
@SpringBootTest
public class RedPacketTest {

    @Test
    public void testDivide() throws Exception {
        Integer amount = 1000;
        Integer total = 10;

        List<Integer> amounts = RedPacketUtil.divideRedPacket(amount, total);
        int sum = 0;
        for (Integer item : amounts) {
            log.info("{} 分，{} 元", item, BigDecimal.valueOf(item).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            sum += item;

        }
        log.info("sum: {}", sum);
    }
}
