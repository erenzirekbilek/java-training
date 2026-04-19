package com.training.client.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduledTaskService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskService.class);

    @Scheduled(fixedRate = 5000)
    public void runEvery5Seconds() {
        logger.info("Scheduled task running at: {}", LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 * * * *")
    public void runEveryHour() {
        logger.info("Hourly task running at: {}", LocalDateTime.now());
    }

    @Scheduled(fixedDelay = 3000, initialDelay = 1000)
    public void runWithDelay() {
        logger.info("Delayed task running at: {}", LocalDateTime.now());
    }
}