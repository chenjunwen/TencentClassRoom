package com.jw.ticket;

import com.jw.ticket.constant.MqQueueConstant;
import com.rabbitmq.client.AMQP;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * rabbitMq消息延时队列
 * @version 1.0
 * @author: chenJenWen
 * @date: 2019/1/20 16:08
 * @email: 643969814@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqDelayedSentTest {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Test
    public void delayedSend(){
        for (int i = 0; i < 10; i++) {
            Date date = new Date();
            final String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm ss").format(date);
            System.out.println("[Delayed]================= send msg:"+dateStr);

            int finalI = i;
            amqpTemplate.convertAndSend(MqQueueConstant.DELAYED_EXCHANGE, MqQueueConstant.DELAYED_ROUTING, dateStr, message1 -> {
                /**延时20秒发送*/
                message1.getMessageProperties().setDelay(10000* finalI);
                /**消息持久化*/
                message1.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message1;
            });
            try {
                Thread.sleep(1000*1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
