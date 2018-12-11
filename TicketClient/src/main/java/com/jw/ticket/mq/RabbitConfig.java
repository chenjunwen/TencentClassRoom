package com.jw.ticket.mq;

import com.jw.ticket.constant.MqQueueConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/10 10:57
 * @email: 643969814@qq.com
 * 基础模式使用mq
 */
@Configuration
public class RabbitConfig {


    /**
     * 初始化购票队列
     * @return
     */

    @Bean(name = MqQueueConstant.TICKET_ORDER_QUEUE)
    public Queue initTicketOrderQueue(){
        return new Queue(MqQueueConstant.TICKET_ORDER_QUEUE);
    }


}
