package com.jw.ticket.mq;

import com.jw.ticket.constant.MqQueueConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/11 14:47
 * @email: 643969814@qq.com
 */
@Configuration
public class RabbitTopicConfig {
    @Bean
    public Queue initTopicOne(){
        return new Queue(MqQueueConstant.TOPIC_ONE_QUEUE);
    }

    @Bean
    public Queue initTopicTwo(){
        return new Queue(MqQueueConstant.TOPIC_TWO_QUEUE);
    }


    @Bean
    public Queue initTopicThree(){
        return new Queue(MqQueueConstant.TOPIC_THREE_QUEUE);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(MqQueueConstant.TOPIC_EXCHANGE);
    }


    /**
     * 将定义的topic_one队列与topic_exchange交换机绑定
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithOne(){
        return BindingBuilder.bind(initTopicOne()).to(topicExchange()).with(MqQueueConstant.TOPIC_ONE_ROUTING_KEY);
    }

    /**
     * 将定义的topic_two队列与topic_exchange交换机绑定
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithTwo(){
        return BindingBuilder.bind(initTopicTwo()).to(topicExchange()).with(MqQueueConstant.TOPIC_TWO_ROUTING_KEY);
    }

    /**
     * 将定义的topic_three队列与topic_exchange交换机绑定
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeWithThree(){
        return BindingBuilder.bind(initTopicThree()).to(topicExchange()).with(MqQueueConstant.TOPIC_THREE_ROUTING_KEY);
    }



}
