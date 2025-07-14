package com.example.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.todolist.config.AppConstants;

@SpringBootApplication
@EnableConfigurationProperties(AppConstants.class)
public class TodolistDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodolistDemoApplication.class, args);
        System.out.println("Hello World !");
    }

}