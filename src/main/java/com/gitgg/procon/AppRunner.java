package com.gitgg.procon;

import com.gitgg.procon.task.Task;
import com.gitgg.procon.task.TaskConsumer;
import com.gitgg.procon.task.TaskProducer;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class AppRunner {

    private final BlockingQueue<Task> tasksQueue;
    private final AppConfig config;

    public AppRunner(AppConfig config) {
        this.tasksQueue = new LinkedBlockingQueue<>(config.getQueueSize());
        this.config = config;
    }

    public void startProcessing(){
        ExecutorService executorService = Executors.newFixedThreadPool(config.getNumOfConsumers() + config.getNumOfProducers());

        for(int i = 0; i < config.getNumOfProducers(); i++){
            executorService.execute(new TaskProducer(tasksQueue, config));
        }

        for(int i = 0; i < config.getNumOfConsumers(); i++){
            executorService.execute(new TaskConsumer(tasksQueue, config));
        }
    }

}
