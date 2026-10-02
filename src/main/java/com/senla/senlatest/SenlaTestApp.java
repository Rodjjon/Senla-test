package com.senla.senlatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SenlaTestApp {

    public static void main(String[] args) {
        SpringApplication.run(SenlaTestApp.class, args);
    }

}
