package com.jw.ticket.repository;

import com.jw.ticket.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author: chenJenWen
 * @Date: 2018/12/4 14:39
 * @email: 643969814@qq.com
 * @Version 1.0
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>, JpaSpecificationExecutor<Ticket> {
}
