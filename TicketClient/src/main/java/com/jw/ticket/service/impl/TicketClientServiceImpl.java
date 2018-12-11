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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm ss");
        final String date = format.format(new Date());
        final String message = "用户" + id + "----------->>" + date;
        logger.info("sendMQBuyTicket==========:"+message);
        amqpTemplate.convertAndSend(MqQueueConstant.TICKET_QUEUE, "123456");
        return "success";
    }
}
