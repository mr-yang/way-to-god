package com.example.core1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Core1Application {

    public static void main(String[] args) {
        SpringApplication.run(Core1Application.class, args);
    }

}
