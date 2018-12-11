package com.jw.ticket.service;

/**
 * @Author: chenJenWen
 * @Date: 2018/11/7 11:56
 * @email: 643969814@qq.com
 * @Version 1.0
 */
public interface TicketServerService {
    /**
     * 用户下单
     * @param ticketId
     * @param userId
     * @return
     */
    String buyTicket(String ticketId, String userId);
}
