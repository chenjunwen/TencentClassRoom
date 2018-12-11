package com.jw.ticket.controller;

import com.jw.ticket.service.TicketServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/7 11:42
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@RestController
public class TicketServerController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TicketServerService ticketServerService;

    @RequestMapping("/buyTicket")
    public String buyTicket(String id, String userName){
        logger.info("开始购票业务");
        final String result = ticketServerService.buyTicket(id, userName);
        logger.info("购票成功业务");
        return result;
    }
}
