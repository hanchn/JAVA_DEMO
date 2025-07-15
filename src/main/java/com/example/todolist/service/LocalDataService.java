package com.example.todolist.service;

import com.example.todolist.entity.Todo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 本地内存数据服务 - 作为数据库的备用方案
 * 注意：这个类仅用于演示，实际项目中应该使用真实的数据库
 */
@Service
public class LocalDataService {
    
    private final Map<Long, Todo> localData = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public LocalDataService() {
        initializeLocalData();
    }
    
    private void initializeLocalData() {
        // 初始化一些本地测试数据
        createLocalTodo("本地任务1", "这是一个本地存储的任务", false);
        createLocalTodo("本地任务2", "另一个本地任务", true);
        createLocalTodo("本地任务3", "第三个本地任务", false);
    }
    
    private Todo createLocalTodo(String title, String description, boolean completed) {
        Todo todo = new Todo();
        todo.setId(idGenerator.getAndIncrement());
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(completed);
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());
        
        localData.put(todo.getId(), todo);
        return todo;
    }
    
    public List<Todo> getAllLocalTodos() {
        return new ArrayList<>(localData.values());
    }
    
    public Optional<Todo> getLocalTodoById(Long id) {
        return Optional.ofNullable(localData.get(id));
    }
    
    public Todo saveLocalTodo(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(idGenerator.getAndIncrement());
            todo.setCreatedAt(LocalDateTime.now());
        }
        todo.setUpdatedAt(LocalDateTime.now());
        localData.put(todo.getId(), todo);
        return todo;
    }
    
    public boolean deleteLocalTodo(Long id) {
        return localData.remove(id) != null;
    }
    
    public List<Todo> searchLocalTodos(String keyword) {
        return localData.values().stream()
                .filter(todo -> todo.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                               (todo.getDescription() != null && todo.getDescription().toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());
    }
    
    public List<Todo> getLocalTodosByCompleted(boolean completed) {
        return localData.values().stream()
                .filter(todo -> todo.getCompleted() == completed)
                .collect(Collectors.toList());
    }
    
    public long getLocalTodoCount() {
        return localData.size();
    }
    
    public void clearLocalData() {
        localData.clear();
    }
}