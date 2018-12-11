package com.jw.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/**
 * 自动增加创建时间和修改时间
 */
@EnableJpaAuditing
public class TicketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketServerApplication.class, args);
    }
}
