package com.jw.ticket.controller;

import com.jw.ticket.service.TicketClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/7 11:42
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@RestController
public class TicketClientController {

    @Autowired
    TicketClientService ticketClientService;

    @GetMapping("sendBuyTicket")
    public String sendMQBuyTicket(String id){
        final String result = ticketClientService.sendMQBuyTicket(id);
        return result;
    }

    /**
     * 提升tomcat性能(减少tomcat主线程的占用量)
     * @return
     */
    @GetMapping("queryCallable")
    public Callable<String> queryCallable(){
        final long startMillis = System.currentTimeMillis();
        System.out.println("主线程开始------->>"+Thread.currentThread());
        Callable<String> resultCallable = () -> {
            final long startMillisF = System.currentTimeMillis();

            System.out.println("副线程开始------->>"+Thread.currentThread());
            final String result = ticketClientService.parallelQuery();
            System.out.println("副线程结束------->>"+Thread.currentThread()+"====耗时====>>"+(System.currentTimeMillis()-startMillisF));
            return result;
        };
        System.out.println("主线程结束------->>"+Thread.currentThread()+"====耗时====>>"+(System.currentTimeMillis()-startMillis));
        return resultCallable;
    }
}
