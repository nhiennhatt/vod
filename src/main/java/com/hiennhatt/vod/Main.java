package com.hiennhatt.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({"com.hiennhatt.vod.controllers", "com.hiennhatt.vod.services", "com.hiennhatt.vod.repositories", "com.hiennhatt.vod.validations", "com.hiennhatt.vod.configs", "com.hiennhatt.vod.utils"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
