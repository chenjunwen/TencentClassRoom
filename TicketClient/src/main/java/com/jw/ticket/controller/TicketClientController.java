package com.jw.ticket.controller;

import com.jw.ticket.service.TicketClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
