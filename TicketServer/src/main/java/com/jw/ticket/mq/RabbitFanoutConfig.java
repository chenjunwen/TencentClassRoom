package com.jw.ticket.mq;

import com.jw.ticket.constant.MqQueueConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/11 11:12
 * @email: 643969814@qq.com
 */
@Configuration
public class RabbitFanoutConfig {
    /**
     * 初始化one队列
     * @return
     */
    @Bean(name = MqQueueConstant.FANOUT_ONE_QUEUE)
    public Queue initFanoutOne(){
        return new Queue(MqQueueConstant.FANOUT_ONE_QUEUE);
    }

    /**
     * 初始化two队列
     * @return
     */
    @Bean
    public Queue initFanoutTwo(){
        return new Queue(MqQueueConstant.FANOUT_TWO_QUEUE);
    }
    /**
     * 初始化three队列
     * @return
     */
    @Bean
    public Queue initFanoutThree(){
        return new Queue(MqQueueConstant.FANOUT_THREE_QUEUE);
    }

    /**
     * 初始化fanout交换机
     * @return
     */
    @Bean(name = MqQueueConstant.FANOUT_EXCHANGE)
    public FanoutExchange initFanoutExchange(){
        return new FanoutExchange(MqQueueConstant.FANOUT_EXCHANGE);
    }

    /**
     * 蒋定义的 fanout_one_queue 队列与 fanout_exchange交换机 绑定
     * @param fanoutExchange
     * @return
     */
    @Bean
    public Binding bindingExchangeWithOne(@Qualifier(MqQueueConstant.FANOUT_EXCHANGE) FanoutExchange fanoutExchange, @Qualifier(MqQueueConstant.FANOUT_ONE_QUEUE) Queue fanoutOneQueue){
        return BindingBuilder.bind(fanoutOneQueue).to(fanoutExchange);
    }

    /**
     * 蒋定义的 fanout_two_queue 队列与 fanout_exchange交换机 绑定
     * @return
     */
    @Bean
    public Binding bingingExchangeWithTwo(){
        return BindingBuilder.bind(initFanoutTwo()).to(initFanoutExchange());
    }

    /**
     * 蒋定义的 fanout_three_queue 队列与 fanout_exchange交换机 绑定
     * @return
     */
    @Bean
    public Binding bingingExchangeWithThree(){
        return BindingBuilder.bind(initFanoutThree()).to(initFanoutExchange());
    }
}
