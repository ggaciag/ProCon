package com.gitgg.procon.task;

import com.gitgg.procon.AppConfig;
import com.gitgg.procon.expression.ExpressionGenerator;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.BlockingQueue;

@Log4j2
public class TaskProducer implements Runnable {

    private static final int WAIT_TIME_MS = 100;
    private final BlockingQueue<Task> tasksQueue;
    private final AppConfig config;

    private boolean waiting = false;

    public TaskProducer(BlockingQueue<Task> tasks, AppConfig config) {
        this.tasksQueue = tasks;
        this.config = config;
    }

    @Override
    public void run() {
        while (true) {
            try {
                produceTask();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void produceTask() throws InterruptedException {

        int remaining = tasksQueue.remainingCapacity();

        if (remaining <= 0) {
            if (!waiting) {
                log.debug("Queue is full, start waiting");
            }
            waiting = true;
        } else if (remaining >= config.getQueueSize() / 2) {

            if (waiting) {
                log.debug("Queue is halfEmpty, stop waiting");
            }
            waiting = false;
        }

        if (waiting) {
            Thread.sleep(WAIT_TIME_MS);
        } else {
            Task task = new Task(ExpressionGenerator.generate(config.getMinExpressionLength(), config.getMaxExpressionLength()));
            tasksQueue.put(task);
        }
    }
}
