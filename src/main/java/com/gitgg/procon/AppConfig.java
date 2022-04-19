package com.gitgg.procon;

import lombok.Data;

@Data
public class AppConfig {

    private int minExpressionLength;
    private int maxExpressionLength;
    private int queueSize;
    private int numOfConsumers;
    private int numOfProducers;

    private boolean slowConsumer = false;
}
