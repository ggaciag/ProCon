package com.gitgg.procon.task;

import com.gitgg.procon.AppConfig;
import com.gitgg.procon.expression.ExpressionEvaluator;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;

@Log4j2
public class TaskConsumer implements Runnable {

    private final BlockingQueue<Task> tasksQueue;
    private final AppConfig config;


    public TaskConsumer(BlockingQueue<Task> tasksQueue, AppConfig config) {
        this.tasksQueue = tasksQueue;
        this.config = config;
    }

    @Override
    public void run() {
        while (true) {
            try {
                handleTask();

                if (config.isSlowConsumer()) {
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void handleTask() throws InterruptedException {
        Task task = tasksQueue.take();

        BigDecimal result = ExpressionEvaluator.evaluate(task.expression());
        log.info("\n Expression: {} \n Result: {}", task.expression(), result);
    }
}
