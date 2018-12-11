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
 * 广播模式发送信息
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQFanoutSendTest {
    @Autowired
    AmqpTemplate amqpTemplate;

    @Test
    public void fanoutSend(){
        Date date = new Date();
        final String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm ss").format(date);
        System.out.println("[fanout] send msg:"+dateStr);
        // 注意 第一个参数是我们交换机的名称 ，第二个参数是routerKey 我们不用管空着就可以，第三个是你要发送的消息
        amqpTemplate.convertAndSend(MqQueueConstant.FANOUT_EXCHANGE,"", dateStr);
    }
}
