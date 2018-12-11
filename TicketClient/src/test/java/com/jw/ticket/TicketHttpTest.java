package com.jw.ticket;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/11 11:50
 * @email: 643969814@qq.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketHttpTest {

    /**
     * 购票接口
     */

    private final String TICKET_SERVER_URL = "http://127.0.0.1:8088/buyTicket";
    /**
     * 用户数量
     */

    private final Integer USER_UNM = 100;
    /**
     * 发枪器
     */
    private CountDownLatch countDownLatch = new CountDownLatch(USER_UNM);

    private final Logger logger = LoggerFactory.getLogger(getClass());


    RestTemplate restTemplate = new RestTemplate();


    /**
     * 模拟用户抢票业务
     * @throws InterruptedException
     */
    @Test
    public void testInvoke() throws InterruptedException {
        for (int i = 0; i < USER_UNM; i++) {
            // 当前计数器减一
            countDownLatch.countDown();
            new Thread(() -> {
                try {
                    // 使当前线程等待
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 只有当计数器为0的时候等待的线程才会触发
                final String result = restTemplate.getForObject(TICKET_SERVER_URL, String.class);
                logger.info("抢票返回的结果：{}", result);

            }).start();
        }
        // 休眠主线程,保证所有子线程执行完
        Thread.sleep(50000);

    }


}
