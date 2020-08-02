package com.debug.middleware.server;

import com.debug.middleware.server.entity.EventInfo;
import com.debug.middleware.server.entity.Person;
import com.debug.middleware.server.rabbit.publisher.BasicPublisher;
import com.debug.middleware.server.rabbit.publisher.ModelPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * RabbitMQ的单元测试
 *
 * @author walker
 * @date 2020/8/1
 */
@SpringBootTest
public class RabbitmqTest {

    @Autowired
    private BasicPublisher basicPublisher;
    @Autowired
    private ModelPublisher modelPublisher;

    @Test
    public void testBasic() {
        String msg = "~~~~这是一串字符串消息~~~~";
        // 生产者实例发送消息
        basicPublisher.sendMsg(msg);
    }

    @Test
    public void testObject() {
        Person person = new Person(1, 20, "大圣", "debug");
        basicPublisher.sendObjectMsg(person);
    }

    @Test
    public void testFanoutExchange() {
        EventInfo info = new EventInfo(1, "增删改查模块", "基于fanoutExchange的消息模型", "这是基于fanoutExchange的消息模型");
        modelPublisher.sendMsg(info);
    }
}
