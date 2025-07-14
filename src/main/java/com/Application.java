package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.todolist.config.AppConstants;

@SpringBootApplication
@EnableConfigurationProperties(AppConstants.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Hello World !");
        System.out.println("测试热更新 !");
    }

}