package com.jw.ticket;

import com.alibaba.fastjson.JSON;
import com.jw.ticket.model.entity.Ticket;
import com.jw.ticket.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TicketServerApplicationTests {
    /**
     * 并发数
     */
    final int concurrentTotal = 100;
    CountDownLatch countDownLatch = new CountDownLatch(concurrentTotal);

    @Autowired
    TicketRepository ticketRepository;

    @Test
    public void contextLoads() {
    }

    /**
     * 保存测试
     */
    @Test
    public void saveTicket() throws InterruptedException {
        CountDownLatch countDown = new CountDownLatch(concurrentTotal);
        final ExecutorService executorService = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < concurrentTotal; i++) {
            countDownLatch.countDown();
            int finalI = i;
            executorService.submit(()->{
                Ticket ticket = new Ticket();
                ticket.setNum(100);
                ticket.setAddress("中国");
                ticket.setTicketName("深圳-南昌"+ finalI);
                try {

                    countDownLatch.await();
                    final Ticket save = ticketRepository.save(ticket);
                    System.out.println(save);
                }catch (Exception e){
                    log.error("修改错误,版本号不一致"+e);
                }
                countDown.countDown();
            });
        }
        executorService.shutdown();
        countDown.await();



    }

    @Test
    public void findAllTicket() throws InterruptedException {
        CountDownLatch countDown = new CountDownLatch(concurrentTotal);
        for (int i = 0; i < concurrentTotal; i++) {
            int finalI = i;
            new Thread(()->{
                try {
                    System.out.println("等待其他子线程--------->"+finalI);
                    countDownLatch.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ticketRepository.findAll(Sort.by(Sort.Direction.DESC, "num"));
                System.out.println(finalI+"------>");
                countDown.countDown();
            }).start();
            countDownLatch.countDown();
        }
        // 等待主线程执行完毕
        countDown.await();
    }

    /**
     * 添加查询测试
     */
    @Test
    public void findByProperty(){
        final Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createDate", "updateDate"));
        final Page<Ticket> tickets = ticketRepository.findAll((root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            //predicates.add(cb.like(root.get("ticketName"), "%" + "南昌" + "%"));
            predicates.add(cb.isNull(root.get("num")));
            return cq.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        }, pageable);
        System.out.println(JSON.toJSONString(tickets, true));
    }
}
