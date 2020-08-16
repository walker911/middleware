package com.debug.middleware.server;

import com.debug.middleware.server.entity.EventInfo;
import com.debug.middleware.server.entity.Person;
import com.debug.middleware.server.rabbitmq.entity.DeadInfo;
import com.debug.middleware.server.rabbitmq.entity.KnowledgeInfo;
import com.debug.middleware.server.rabbitmq.publisher.*;
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
    @Autowired
    private KnowledgePublisher knowledgePublisher;
    @Autowired
    private KnowledgeManualPublisher knowledgeManualPublisher;
    @Autowired
    private DeadPublisher deadPublisher;

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

    @Test
    public void testDirectExchange() {
        EventInfo info = new EventInfo(1, "模块1", "directExchange", "direct");
        modelPublisher.sendMsgDirectOne(info);

        info = new EventInfo(2, "模块2", "directExchange", "direct");
        modelPublisher.sendMsgDirectTwo(info);
    }

    @Test
    public void testTopicExchange() {
        String msg = "这是TopicExchange消息模型的消息";
        String routingKeyOne = "local.middleware.mq.topic.routing.java.key";
        String routingKeyTwo = "local.middleware.mq.topic.routing.php.python.key";
        String routingKeyThree = "local.middleware.mq.topic.routing.key";
//        modelPublisher.sendMsgTopic(msg, routingKeyOne);
//        modelPublisher.sendMsgTopic(msg, routingKeyTwo);
        modelPublisher.sendMsgTopic(msg, routingKeyThree);
    }

    @Test
    public void testAuto() {
        KnowledgeInfo info = new KnowledgeInfo();
        info.setId(10010);
        info.setCode("auto");
        info.setMode("基于AUTO的消息确认消费模式");
        knowledgePublisher.sendAutoMsg(info);
    }

    @Test
    public void testManual() {
        KnowledgeInfo info = new KnowledgeInfo();
        info.setId(10011);
        info.setCode("manual");
        info.setMode("基于MANUAL的消息确认消费模式");
        knowledgeManualPublisher.sendMsg(info);
    }

    @Test
    public void testDead() throws Exception {
        DeadInfo info = new DeadInfo(1, "~~~我是第一则消息~~~");
        deadPublisher.sendMsg(info);
        info = new DeadInfo(2, "~~~~我是第二则消息~~~");
        deadPublisher.sendMsg(info);
        Thread.sleep(30000);
    }
}
