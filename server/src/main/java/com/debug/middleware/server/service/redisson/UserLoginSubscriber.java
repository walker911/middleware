package com.debug.middleware.server.service.redisson;

import com.debug.middleware.server.dto.UserLoginDTO;
import com.debug.middleware.server.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
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
public class UserLoginSubscriber implements ApplicationRunner, Ordered {

    private static final String TOPIC_KEY = "Redisson:User:Login:Topic:Key";

    private final RedissonClient redissonClient;
    private final SysLogService sysLogService;

    public UserLoginSubscriber(RedissonClient redissonClient, SysLogService sysLogService) {
        this.redissonClient = redissonClient;
        this.sysLogService = sysLogService;
    }

    /**
     * 监听消费
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            RTopic topic = redissonClient.getTopic(TOPIC_KEY);
            topic.addListener(UserLoginDTO.class, ((charSequence, dto) -> {
                log.info("记录用户登录成功后的轨迹-消费者-监听消费到消息：{}", dto);
                if (null != dto) {
                    sysLogService.recordLog(dto);
                }
            }));
        } catch (Exception e) {
            log.error("记录用户登录成功后的轨迹-消费者-发生异常", e.fillInStackTrace());
        }
    }

    /**
     * 设置项目启动时也跟着启动
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
