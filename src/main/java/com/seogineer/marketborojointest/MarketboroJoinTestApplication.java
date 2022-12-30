package com.seogineer.marketborojointest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MarketboroJoinTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketboroJoinTestApplication.class, args);
    }

}
