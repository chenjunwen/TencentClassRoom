package com.jw.ticket.constant;

/**
 * @version 1.0
 * @author: chenJenWen
 * @date: 2018/12/10 11:24
 * @email: 643969814@qq.com
 */
public interface MqQueueConstant {

    /**
     * 购票队列
     */
    String TICKET_QUEUE = "ticket_queue";

    /**
     * 订单队列
     */
    String TICKET_ORDER_QUEUE = "ticket_order_queue";

    //=====================fanout(广播)模式===============
    /**
     * fanout交换器
     */
    String FANOUT_EXCHANGE = "fanout_exchange";

    /**
     * fanout队列
     */
    String FANOUT_ONE_QUEUE = "fanout_one_queue";

    String FANOUT_TWO_QUEUE = "fanout_two_queue";

    String FANOUT_THREE_QUEUE = "fanout_three_queue";


    //=====================topic模式=======================
    /**
     * TOPIC交换器
     */
    String TOPIC_EXCHANGE = "topic_exchange";

    /**
     * TOPIC队列
     */
    String TOPIC_ONE_QUEUE = "topic_one_queue";

    String TOPIC_TWO_QUEUE = "topic_two_queue";

    String TOPIC_THREE_QUEUE = "topic_three_queue";


    /**
     * topic 路由键（通配符模式）
     * TOPIC_ONE_ROUTING_KEY 的key为topic.msg 那么他只会接收包含topic.msg的消息
     * TOPIC_TWO_ROUTING_KEY 的key为topic.# 那么他只会接收topic开头的消息
     * TOPIC_THREE_ROUTING_KEY 的key为topic.*.Z那么他只会接收topic.B.z这样格式的消息
     */
    String TOPIC_ONE_ROUTING_KEY = "topic.msg";

    String TOPIC_TWO_ROUTING_KEY = "topic.#";

    String TOPIC_THREE_ROUTING_KEY = "topic.*.z";
}
