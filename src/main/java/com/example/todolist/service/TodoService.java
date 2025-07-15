package com.example.todolist.service;

import com.example.todolist.entity.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    
    @Autowired
    private TodoRepository todoRepository;
    
    // 获取所有待办事项
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    
    // 根据ID获取待办事项
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }
    
    // 创建新的待办事项
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    
    // 更新待办事项
    public Todo updateTodo(Long id, Todo todoDetails) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setTitle(todoDetails.getTitle());
            todo.setDescription(todoDetails.getDescription());
            todo.setCompleted(todoDetails.getCompleted());
            return todoRepository.save(todo);
        }
        return null;
    }
    
    // 删除待办事项
    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // 切换完成状态
    public Todo toggleComplete(Long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setCompleted(!todo.getCompleted());
            return todoRepository.save(todo);
        }
        return null;
    }
    
    // 获取未完成的任务
    public List<Todo> getPendingTodos() {
        return todoRepository.findPendingTodos();
    }
    
    // 获取已完成的任务
    public List<Todo> getCompletedTodos() {
        return todoRepository.findCompletedTodos();
    }
    
    // 根据标题搜索
    public List<Todo> searchTodos(String keyword) {
        return todoRepository.findByTitleContainingIgnoreCase(keyword);
    }
}