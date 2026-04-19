package com.training.client.thread;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class UserThreadService {
    private final ExecutorService executor = Executors.newFixedThreadPool(5);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    public Future<String> submitTask(String task) {
        return executor.submit(() -> {
            Thread.sleep(1000);
            return "Task completed: " + task;
        });
    }

    public void executeTask(Runnable task) {
        executor.execute(task);
    }

    public List<Future<String>> submitMultiple(List<String> tasks) {
        List<Future<String>> futures = new ArrayList<>();
        for (String task : tasks) {
            futures.add(submitTask(task));
        }
        return futures;
    }

    public void scheduleTask(Runnable task, long delay) {
        scheduler.schedule(task, delay, TimeUnit.SECONDS);
    }

    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period) {
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    public void shutdown() {
        executor.shutdown();
        scheduler.shutdown();
    }
}