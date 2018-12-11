package com.jw.ticket.mq.consumer;

import com.jw.ticket.constant.MqQueueConstant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/10 14:13
 * @email: 643969814@qq.com
 * 普通工作队列模式处理购票业务请求mq
 */
@Component
public class TopicConsumer {

    @Autowired
    AmqpTemplate amqpTemplate;

    @RabbitListener(queues = MqQueueConstant.TOPIC_ONE_QUEUE)
    public void recievedOne(String msg){
        System.out.println("[topic.one] recieved message:" + msg);
    }


    @RabbitListener(queues = MqQueueConstant.TOPIC_TWO_QUEUE)
    public void recievedTwo(String msg){
        System.out.println("[topic.two] recieved message:" + msg);
    }


    @RabbitListener(queues = MqQueueConstant.TOPIC_THREE_QUEUE)
    public void recievedThree(String msg){
        System.out.println("[topic.three] recieved message:" + msg);
    }
}
