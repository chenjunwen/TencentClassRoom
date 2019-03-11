package com.jw.ticket.mq.consumer;

import com.jw.ticket.constant.MqQueueConstant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/10 14:13
 * @email: 643969814@qq.com
 * mq延时队列处理
 */
@Component
public class DelayedConsumer {

    @RabbitListener(queues = MqQueueConstant.DELAYED_QUEUE)
    public void recievedOne(String msg){
        final String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm ss").format(new Date());

        System.out.println(dateStr+"[delayed] recieved message:" + msg);
    }


}
