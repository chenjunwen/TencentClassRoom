package com.jw.ticket.mq.consumer;

import com.jw.ticket.constant.MqQueueConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/11 11:30
 * @email: 643969814@qq.com
 * fanout（广播）消费队列
 */
@Component
public class FanoutConsumer {
    /**
     * 消费one队列推送的消息
     * @param msg
     */
    @RabbitListener(queues = MqQueueConstant.FANOUT_ONE_QUEUE)
    public void recievedOne(String msg){
        System.out.println("[fanout_one_queue] recieved message:" + msg);
    }

    /**
     * 消费two队列推送的消息
     * @param msg
     */
    @RabbitListener(queues = MqQueueConstant.FANOUT_TWO_QUEUE)
    public void recievedTwo(String msg){
        System.out.println("[fanout_two_queue] recieved message:" + msg);
    }

    /**
     * 消费three队列推送的消息
     * @param msg
     */
    @RabbitListener(queues = MqQueueConstant.FANOUT_THREE_QUEUE)
    public void recievedThree(String msg){
        System.out.println("[fanout_three_queue] recieved message:" + msg);
    }
}
