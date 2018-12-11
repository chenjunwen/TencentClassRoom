package com.jw.ticket;

import com.jw.ticket.constant.MqQueueConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/11 11:46
 * @email: 643969814@qq.com
 * 普通工作队列模式发送信息
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQSendTest {
    final int USER_NUM = 1000;
    CountDownLatch countDownLatch = new CountDownLatch(USER_NUM);

    @Autowired
    AmqpTemplate amqpTemplate;
    @Test
    public void testSendRabbitBuyTicket() throws InterruptedException {
        for (int i = 0; i < USER_NUM; i++) {
            countDownLatch.countDown();
            new Thread(()->{
                try {
                    countDownLatch.await();
                    String ticketId = "006ba9f6359147198156e0602ef7d55b";
                    amqpTemplate.convertAndSend(MqQueueConstant.TICKET_QUEUE, ticketId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(100000);
    }
}
