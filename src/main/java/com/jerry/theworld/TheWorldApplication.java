package com.jerry.theworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class TheWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(TheWorldApplication.class, args);
    }

}
