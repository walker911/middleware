package com.debug.middleware.server.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *
 * </p>
 *
 * @author mu qin
 * @date 2020/7/17
 */
public class RedPacketUtil {

    /**
     * 二倍均值法
     *
     * @param totalAmount
     * @param totalPeopleNum
     * @return
     */
    public static List<Integer> divideRedPacket(Integer totalAmount, Integer totalPeopleNum) {
        List<Integer> amounts = new ArrayList<>();
        if (totalAmount > 0 && totalPeopleNum > 0) {
            Random random = new Random();
            while (totalPeopleNum - 1 > 0) {
                int amount = random.nextInt(totalAmount / totalPeopleNum * 2 - 1) + 1;
                totalAmount -= amount;
                totalPeopleNum--;
                amounts.add(amount);
            }
            amounts.add(totalAmount);
        }
        return amounts;
    }

}
