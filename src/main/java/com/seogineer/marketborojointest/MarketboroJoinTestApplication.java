package com.seogineer.marketborojointest;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT12H")
@EnableJpaAuditing
@SpringBootApplication
public class MarketboroJoinTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketboroJoinTestApplication.class, args);
    }

}
