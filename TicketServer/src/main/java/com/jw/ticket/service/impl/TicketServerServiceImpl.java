package com.jw.ticket.service.impl;

import com.alibaba.fastjson.JSON;
import com.jw.ticket.model.entity.Ticket;
import com.jw.ticket.repository.TicketRepository;
import com.jw.ticket.service.TicketServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/7 11:58
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@Service
public class TicketServerServiceImpl implements TicketServerService {
    @Autowired
    TicketRepository ticketRepository;
    /**
     * 用户下单
     *
     * @param ticketId
     * @param userId
     * @return
     */
    @Override
    public String buyTicket(String ticketId, String userId) {
        Ticket ticket;
        if(ticketId == null){
            ticket = ticketRepository.findAll().get(0);
        }else{
            ticket = ticketRepository.findById(ticketId).get();
        }
        return JSON.toJSONString(ticket);
    }
}
