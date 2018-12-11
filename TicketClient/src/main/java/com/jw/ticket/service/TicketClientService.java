package com.jw.ticket.service;


/**
 * @Author: chenJenWen
 * @Date: 2018/12/4 10:31
 * @email: 643969814@qq.com
 * @Version 1.0
 */
public interface TicketClientService {
    /**
     * 抢票
     * @param id
     * @return
     */
    String sendMQBuyTicket(String id);
}
