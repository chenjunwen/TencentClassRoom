package com.jw.ticket.mq.consumer;

import com.jw.ticket.constant.MqQueueConstant;
import com.jw.ticket.service.TicketServerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/10 14:13
 * @email: 643969814@qq.com
 * @RabbitListener 和 @RabbitHandler 搭配使用
 * @RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用
 * @RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型
 * 普通工作队列模式处理购票业务请求mq
 */
@Component
@RabbitListener(queues = MqQueueConstant.TICKET_QUEUE)
public class TicketConsumer {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    TicketServerService ticketServerService;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 处理下单业务逻辑（减库存，生成订单）
     * @param msg
     */
    @RabbitHandler
    public void process(String msg){
        // 模拟下单操作
        System.out.println("出票成功--------------rabbitMq");
        try {
            // 休眠一秒看是同步还是异步
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 模拟查询订单数据
        System.out.println("msg-------->>:"+msg);
        final String s = ticketServerService.buyTicket(null, null);
        // 通知客户端处理结果(使用topic模式发送)
        amqpTemplate.convertAndSend(MqQueueConstant.TICKET_ORDER_QUEUE, format.format(new Date())+"--------------order："+s);
        final Object o = amqpTemplate.convertSendAndReceive(MqQueueConstant.TICKET_ORDER_QUEUE, "123");
    }


    @RabbitHandler
    public void process2(byte[] message) {
        System.out.println("process2:"+new String(message));
    }
}
