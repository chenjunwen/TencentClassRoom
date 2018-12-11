package com.jw.ticket.mq;

import com.jw.ticket.constant.MqQueueConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/10 16:03
 * @email: 643969814@qq.com
 * 普通工作队列模式
 */
@Component
public class TicketOrderConsumer {

   @RabbitListener(queues = MqQueueConstant.TICKET_ORDER_QUEUE)
   public void recieved(String msg){
        System.out.println("TicketClient购票结果通知------------->"+msg);
   }
}
