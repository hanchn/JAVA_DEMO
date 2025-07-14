package com.example.todolist.service.repository.impl;

import com.example.todolist.dto.response.TodoResponse;
import com.example.todolist.service.repository.TodoRepository;

import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class TodoRepositoryImpl implements TodoRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(TodoRepositoryImpl.class);
    
    // 使用内存存储，实际项目中应该连接数据库
    private final Map<Integer, TodoResponse> todoStorage = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    
    // 构造函数，初始化测试数据
    public TodoRepositoryImpl() {
        initTestData();
    }
    
    private void initTestData() {
        TodoResponse todo1 = new TodoResponse();
        todo1.setId(idGenerator.getAndIncrement());
        todo1.setTitle("学习 Spring Boot");
        todo1.setDescription("深入学习Spring Boot框架");
        todo1.setCompleted(false);
        todo1.setPriority("HIGH");
        todo1.setCategory("学习");
        todo1.setCreatedAt(LocalDateTime.now());
        todo1.setUpdatedAt(LocalDateTime.now());
        todoStorage.put(todo1.getId(), todo1);
        
        TodoResponse todo2 = new TodoResponse();
        todo2.setId(idGenerator.getAndIncrement());
        todo2.setTitle("完成项目开发");
        todo2.setDescription("完成TodoList项目的开发工作");
        todo2.setCompleted(false);
        todo2.setPriority("MEDIUM");
        todo2.setCategory("工作");
        todo2.setCreatedAt(LocalDateTime.now());
        todo2.setUpdatedAt(LocalDateTime.now());
        todoStorage.put(todo2.getId(), todo2);
    }
    
    @Override
    public List<TodoResponse> findAll() {
        logger.debug("查找所有待办事项，当前数量: {}", todoStorage.size());
        return new ArrayList<>(todoStorage.values());
    }
    
    @Override
    public Optional<TodoResponse> findById(Integer id) {
        logger.debug("根据ID查找待办事项: {}", id);
        return Optional.ofNullable(todoStorage.get(id));
    }
    
    @Override
    public TodoResponse save(TodoResponse todo) {
        if (todo.getId() == null) {
            todo.setId(idGenerator.getAndIncrement());
            logger.debug("创建新的待办事项，ID: {}", todo.getId());
        } else {
            logger.debug("更新待办事项，ID: {}", todo.getId());
        }
        
        todoStorage.put(todo.getId(), todo);
        return todo;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        logger.debug("删除待办事项，ID: {}", id);
        return todoStorage.remove(id) != null;
    }
    
    @Override
    public List<TodoResponse> findByConditions(String keyword, Boolean completed, String priority, String category) {
        logger.debug("根据条件查找待办事项");
        
        return todoStorage.values().stream()
            .filter(todo -> keyword == null || 
                todo.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                (todo.getDescription() != null && todo.getDescription().toLowerCase().contains(keyword.toLowerCase())))
            .filter(todo -> completed == null || todo.getCompleted().equals(completed))
            .filter(todo -> priority == null || priority.equals(todo.getPriority()))
            .filter(todo -> category == null || category.equals(todo.getCategory()))
            .collect(Collectors.toList());
    }
}