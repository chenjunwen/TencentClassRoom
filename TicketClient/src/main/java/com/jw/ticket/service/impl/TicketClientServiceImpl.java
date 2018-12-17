package com.jw.ticket.service.impl;

import com.jw.ticket.constant.MqQueueConstant;
import com.jw.ticket.service.TicketClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: chenJenWen
 * @Date: 2018/12/6 14:22
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@Service
public class TicketClientServiceImpl implements TicketClientService {
    final Logger logger = LoggerFactory.getLogger(TicketClientServiceImpl.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    /**
     * 抢票
     *
     * @param id
     * @return
     */
    @Override
    public String sendMQBuyTicket(String id) {
        // 基于jvm锁操作
        final ReentrantLock reentrantLock = new ReentrantLock();
        try {
            reentrantLock.lock();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm ss");
            final String date = format.format(new Date());
            final String message = "用户" + id + "----------->>" + date;
            logger.info("sendMQBuyTicket==========:"+message);
            amqpTemplate.convertAndSend(MqQueueConstant.TICKET_QUEUE, "123456");
        }catch (Exception e){

        }finally {
            reentrantLock.unlock();
        }

        return "success";
    }

    /**
     * 模拟调用多个系统使用并行的方式处理
     *
     * @return
     */
    @Override
    public String parallelQuery() {
        Map<String,String> result = new HashMap<>();
        final ExecutorService executorService = Executors.newCachedThreadPool();
        // 模拟查询用户系统
        final Future<Map<String, String>> userFuture = executorService.submit(() -> {
            final long start = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "========开始查询用户信息============>>"+start);
            Map<String, String> user = new HashMap<>(1);
            user.put("userName", "cjw");
            user.put("userPhone", "17608081222");
            TimeUnit.MILLISECONDS.sleep(100 * new Random().nextInt(100));
            System.out.println(Thread.currentThread().getName() + "查询用户信息结束=========耗时===>>" + (System.currentTimeMillis()-start));
            return user;
        });

        // 模拟查询积分系统
        final Future<Map<String, String>> integralFuture = executorService.submit(() -> {
            final long start = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "========开始查询积分信息============>>" + start);
            Map<String, String> integral = new HashMap<>(1);
            integral.put("integral", "1000");
            integral.put("grade", "黄金会员");
            TimeUnit.MILLISECONDS.sleep(100 * new Random().nextInt(100));
            System.out.println(Thread.currentThread().getName() + "查询积分信息结束=========耗时===>>"+(System.currentTimeMillis()-start));
            return integral;
        });

        // 模拟查询余额系统
        final Future<Map<String, String>> balanceFuture = executorService.submit(() -> {
            final long start = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "========开始查询余额信息============>>"+start);
            Map<String, String> balance = new HashMap<>(1);
            balance.put("balance", "1000000");
            TimeUnit.MILLISECONDS.sleep(100 * new Random().nextInt(100));
            System.out.println(Thread.currentThread().getName() + "查询余额信息结束=========耗时===>>" + +(System.currentTimeMillis()-start)+"======毫秒");
            return balance;
        });
        executorService.shutdown();
        try {
            result.putAll(userFuture.get());
            result.putAll(integralFuture.get());
            result.putAll(balanceFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
