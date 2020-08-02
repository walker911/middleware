package com.debug.middleware.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * <p>
 * RabbitMQ 配置
 * </p>
 *
 * @author mu qin
 * @date 2020/7/27
 */
@Slf4j
@Configuration
public class RabbitmqConfig {

    private final CachingConnectionFactory connectionFactory;
    private final SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
    private final Environment env;

    public RabbitmqConfig(CachingConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer, Environment env) {
        this.connectionFactory = connectionFactory;
        this.factoryConfigurer = factoryConfigurer;
        this.env = env;
    }

    /**
     * 单个消费者实例配置
     *
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        // 定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        // 传输格式: Json
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 并发消费者实例的初始数量
        factory.setConcurrentConsumers(1);
        // 并发消费者实例的最大数量
        factory.setMaxConcurrentConsumers(1);
        // 并发消费者实例中每个实例拉取的消息数量
        factory.setPrefetchCount(1);

        return factory;
    }

    /**
     * 多个消费者实例的配置
     *
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        // 定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置容器工厂所用的实例
        factoryConfigurer.configure(factory, connectionFactory);
        // 传输格式: Json
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 消息的确认消费模式，NONE-表示不需要确认消费
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        // 并发消费者实例的初始数量
        factory.setConcurrentConsumers(10);
        // 并发消费者实例的最大数量
        factory.setMaxConcurrentConsumers(15);
        // 并发消费者实例中每个实例拉取的消息数量
        factory.setPrefetchCount(10);

        return factory;
    }

    /**
     * 配置RabbitMQ发送消息的操作组件
     *
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        // 设置发送消息后进行确认
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        // 设置发送消息后返回确认信息
        connectionFactory.setPublisherReturns(true);
        // 构建发送消息组件实例对象
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        // 发送消息后，若发送成功，则输出"消息发送成功"的反馈消息
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("消息发送成功：correlationData: {}, ack: {}, cause: {}", correlationData, ack, cause);
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息丢失：message: {}, replyCode: {}, replyText: {}, exchange: {}, routingKey: {}",
                    message, replyCode, replyText, exchange, routingKey);
        });

        return rabbitTemplate;
    }

    /**
     * 创建消息模型 - fanoutExchange
     * 队列1
     */
    @Bean(name = "fanoutQueueOne")
    public Queue fanoutQueueOne() {
        return new Queue(env.getProperty("mq.fanout.queue.one.name"), true);
    }

    /**
     * 创建队列2
     *
     * @return
     */
    @Bean(name = "fanoutQueueTwo")
    public Queue fanoutQueueTwo() {
        return new Queue(env.getProperty("mq.fanout.queue.two.name"), true);
    }

    /**
     * 创建交换机 - fanoutExchange
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(env.getProperty("mq.fanout.exchange.name"), true, false);
    }

    /**
     * 创建绑定1
     *
     * @return
     */
    @Bean
    public Binding fanoutBindingOne() {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }

    /**
     * 创建绑定2
     *
     * @return
     */
    @Bean
    public Binding fanoutBindingTwo() {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }

    /**
     * 创建消息模型 - directExchange
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(env.getProperty("mq.direct.exchange.name"), true, false);
    }

    @Bean(name = "directQueueOne")
    public Queue directQueueOne() {
        return new Queue(env.getProperty("mq.direct.queue.one.name"), true);
    }

    @Bean(name = "directQueueTwo")
    public Queue directQueueTwo() {
        return new Queue(env.getProperty("mq.direct.queue.two.name"), true);
    }

    @Bean
    public Binding directBindingOne() {
        return BindingBuilder.bind(directQueueOne()).to(directExchange()).with(env.getProperty("mq.direct.routing.key.one.name"));
    }

    @Bean
    public Binding directBindingTwo() {
        return BindingBuilder.bind(directQueueTwo()).to(directExchange()).with(env.getProperty("mq.direct.routing.key.two.name"));
    }
}
