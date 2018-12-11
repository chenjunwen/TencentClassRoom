package com.jw.ticket;

import com.jw.ticket.constant.MqQueueConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/11 11:46
 * @email: 643969814@qq.com
 * topic模式发送信息
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTopicSendTest {
    @Autowired
    AmqpTemplate amqpTemplate;

    public void topicOneSend(){
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm ss").format(date);
        dateStr = "[topic.msg] send msg:"+dateStr;
        System.out.println(dateStr);
        // 注意 第一个参数是我们交换机的名称 ，第二个参数是routerKey 我们不用管空着就可以，第三个是你要发送的消息
        amqpTemplate.convertAndSend(MqQueueConstant.TOPIC_EXCHANGE, MqQueueConstant.TOPIC_ONE_ROUTING_KEY, dateStr);
    }


    public void topicTwoSend(){
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm ss").format(date);
        dateStr = "[topic.goods.msg] send msg:"+dateStr;
        System.out.println(dateStr);
        // 注意 第一个参数是我们交换机的名称 ，第二个参数是routerKey 我们不用管空着就可以，第三个是你要发送的消息
        amqpTemplate.convertAndSend(MqQueueConstant.TOPIC_EXCHANGE, "topic.good.msg", dateStr);
    }


    public void topicThreeSend(){
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm ss").format(date);
        dateStr = "[topic.msg.z] send msg:"+dateStr;
        System.out.println(dateStr);
        // 注意 第一个参数是我们交换机的名称 ，第二个参数是routerKey 我们不用管空着就可以，第三个是你要发送的消息
        amqpTemplate.convertAndSend(MqQueueConstant.TOPIC_EXCHANGE, "topic.m.z", dateStr);
    }

    @Test
    public void topicSend(){
        topicOneSend();
        topicTwoSend();
        topicThreeSend();
    }
}
