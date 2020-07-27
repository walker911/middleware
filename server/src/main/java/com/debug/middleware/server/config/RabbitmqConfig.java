package com.debug.middleware.server.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * RabbitMQ 配置
 * </p>
 *
 * @author mu qin
 * @date 2020/7/27
 */
@Configuration
public class RabbitmqConfig {

    private final CachingConnectionFactory connectionFactory;
    private final SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    public RabbitmqConfig(CachingConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer) {
        this.connectionFactory = connectionFactory;
        this.factoryConfigurer = factoryConfigurer;
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
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {

        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {

        });

        return rabbitTemplate;
    }
}
