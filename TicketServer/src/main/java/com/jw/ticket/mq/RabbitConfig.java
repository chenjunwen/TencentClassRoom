package com.jw.ticket.mq;

import com.jw.ticket.constant.MqQueueConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/10 10:26
 * @email: 643969814@qq.com
 */
@Configuration
public class RabbitConfig {

    /**
     * 初始化购票队列
     * @return
     */
    @Bean(name = MqQueueConstant.TICKET_QUEUE)
    public Queue initTicketQueue(){
       return new Queue(MqQueueConstant.TICKET_QUEUE);
    }
}
