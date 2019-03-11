package com.jw.ticket.mq;

import com.jw.ticket.constant.MqQueueConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/11 14:47
 * @email: 643969814@qq.com
 * 延时消息队列也叫死信队列
 */
@Configuration
public class RabbitDelayedConfig {


    /**
     * 交换机设置
     * 开启了延时消息
     * @return
     */
    @Bean
    public DirectExchange defaultExchange() {
        DirectExchange directExchange = new DirectExchange(MqQueueConstant.DELAYED_EXCHANGE, true, false);
        /**只需简单一步开启延时消息，md找了好久，原来这么简单*/
        directExchange.setDelayed(true);
        return directExchange;
    }

    /**
     * 队列设置
     * @return
     */
    @Bean
    public Queue notifyQueue() {
        /**消息持久化*/
        return new Queue(MqQueueConstant.DELAYED_QUEUE,true);
    }

    /**
     * 绑定队列到交换机
     * @return
     */
    @Bean
    public Binding bindingNotify() {
        return BindingBuilder.bind(notifyQueue()).to(defaultExchange()).with(MqQueueConstant.DELAYED_ROUTING);
    }


}
