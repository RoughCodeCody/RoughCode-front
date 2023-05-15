package com.cody.roughcode;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@EnableAspectJAutoProxy
public class RoughcodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoughcodeApplication.class, args);
    }

}
