package com.cody.roughcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RoughcodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoughcodeApplication.class, args);
    }

}
