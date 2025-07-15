package com.example.app.dataaccess.impl;

import com.example.app.dataaccess.TodoDataAccess;
import com.example.app.entity.Todo;
import com.example.app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Todo数据访问实现类
 */
@Component
public class TodoDataAccessImpl implements TodoDataAccess {
    
    @Autowired
    private TodoRepository todoRepository;
    
    @Override
    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }
    
    @Override
    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }
    
    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }
    
    @Override
    public List<Todo> findByCompleted(Boolean completed) {
        return todoRepository.findByCompleted(completed);
    }
    
    @Override
    public List<Todo> findByPriority(String priority) {
        return todoRepository.findByPriority(priority);
    }
    
    @Override
    public List<Todo> findByCategory(String category) {
        return todoRepository.findByCategory(category);
    }
    
    @Override
    public List<Todo> searchByKeyword(String keyword) {
        return todoRepository.searchByKeyword(keyword);
    }
    
    @Override
    public List<Todo> findHighPriorityPendingTodos() {
        return todoRepository.findHighPriorityPendingTodos();
    }
    
    @Override
    public long countCompletedTodos() {
        return todoRepository.countCompletedTodos();
    }
    
    @Override
    public long countPendingTodos() {
        return todoRepository.countPendingTodos();
    }
    
    @Override
    public long countByPriority(String priority) {
        return todoRepository.countByPriority(priority);
    }
    
    @Override
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return todoRepository.existsById(id);
    }
}