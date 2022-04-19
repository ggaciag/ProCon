package com.gitgg.procon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class ProConApplication implements CommandLineRunner {


    @Bean
    @ConfigurationProperties(prefix = "application")
    public AppConfig appConfig() {
        return new AppConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProConApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AppRunner appRunner = new AppRunner(appConfig());
        appRunner.startProcessing();
    }
}
