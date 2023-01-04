package com.seogineer.marketborojointest.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScheduler {
    private final ReserveRepository reserveRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @SchedulerLock(
            name = "scheduler_lock",
            lockAtLeastFor = "PT23H",
            lockAtMostFor = "PT23H"
    )
    public void batchExpiredReserve(){
        log.info("batchExpiredReserve() 실행");
        reserveRepository.findCreatedDateOneYearsAgo().forEach(Reserve::delete);
    }
}
