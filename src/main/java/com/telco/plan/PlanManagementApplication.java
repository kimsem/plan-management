package com.telco.plan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PlanManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanManagementApplication.class, args);
    }
}