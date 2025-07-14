package com.example.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello! Spring Boot TodoList Demo is running!";
    }
    
    @GetMapping("/health")
    public String health() {
        return "Application is healthy!";
    }
}