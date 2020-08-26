package com.debug.middleware.server.service.redisson;

import com.debug.middleware.server.dto.UserLoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 记录用户登录成功后轨迹 - 生产者
 * </p>
 *
 * @author mu qin
 * @date 2020/8/26
 */
@Slf4j
@Component
public class UserLoginPublisher {

    private static final String TOPIC_KEY = "Redisson:User:Login:Topic:Key";

    private final RedissonClient redissonClient;

    public UserLoginPublisher(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 异步发送消息
     *
     * @param dto
     */
    public void sendMsg(UserLoginDTO dto) {
        try {
            if (dto != null) {
                log.info("记录用户登录成功的轨迹-生产者-发送消息：{}", dto);
                // 创建主题
                RTopic topic = redissonClient.getTopic(TOPIC_KEY);
                // 发布消息
                topic.publish(dto);
            }
        } catch (Exception e) {
            log.error("记录用户登录成功后的轨迹-生产者-发生异常：{}", dto, e.fillInStackTrace());
        }
    }
}
