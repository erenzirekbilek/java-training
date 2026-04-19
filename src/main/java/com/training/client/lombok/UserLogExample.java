package com.training.client.lombok;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserLogExample {
    public void performAction() {
        log.info("Action performed");
        log.warn("Warning message");
        log.error("Error occurred");
    }
}