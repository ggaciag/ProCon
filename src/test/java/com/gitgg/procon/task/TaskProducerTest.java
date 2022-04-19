package com.gitgg.procon.task;

import com.gitgg.procon.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskProducerTest {

    private static final int QUEUE_SIZE = 6;

    AppConfig appConfig;
    BlockingQueue<Task> tasksQueue;
    TaskProducer taskProducer;

    @BeforeEach
    public void setup(){
        appConfig = createAppConfig();
        tasksQueue = new LinkedBlockingQueue<>(QUEUE_SIZE);
        taskProducer = new TaskProducer(tasksQueue, appConfig);
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    public void producerWillWaitWhenQueueIsFull() throws InterruptedException {
        //When queue is full
        for(int i = 0; i < QUEUE_SIZE; i++){
            taskProducer.produceTask();
        }

        //Then should not block
        taskProducer.produceTask();

        //When queue is more then half full
        tasksQueue.take();
        tasksQueue.take();

        //Then should not add new tasks
        taskProducer.produceTask();
        assertEquals(2, tasksQueue.remainingCapacity());

        //When queue half empty
        tasksQueue.take();
        //Then should add new tasks
        taskProducer.produceTask();
        assertEquals(QUEUE_SIZE - 2, tasksQueue.size());
    }


    @Test
    public void tasksAddedToQueueHaveExpressions() throws InterruptedException {
        //When
        taskProducer.produceTask();
        //Then
        assertEquals(1, tasksQueue.size());
        String expression = tasksQueue.take().expression();
        assertTrue(appConfig.getMinExpressionLength() <= expression.length());
        assertTrue(appConfig.getMaxExpressionLength() >= expression.length());
    }

    private AppConfig createAppConfig(){
        AppConfig appConfig = new AppConfig();
        appConfig.setMinExpressionLength(10);
        appConfig.setMaxExpressionLength(10);
        appConfig.setQueueSize(6);

        return appConfig;
    }

}