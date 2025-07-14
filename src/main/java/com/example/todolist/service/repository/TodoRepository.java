package com.example.todolist.service.repository;

import com.example.todolist.dto.response.TodoResponse;

import java.util.List;
import java.util.Optional;

/**
 * 待办事项数据访问接口
 */
public interface TodoRepository {
    
    /**
     * 查找所有待办事项
     */
    List<TodoResponse> findAll();
    
    /**
     * 根据ID查找待办事项
     */
    Optional<TodoResponse> findById(Integer id);
    
    /**
     * 保存待办事项
     */
    TodoResponse save(TodoResponse todo);
    
    /**
     * 根据ID删除待办事项
     */
    boolean deleteById(Integer id);
    
    /**
     * 根据条件查找待办事项
     */
    List<TodoResponse> findByConditions(String keyword, Boolean completed, String priority, String category);
}