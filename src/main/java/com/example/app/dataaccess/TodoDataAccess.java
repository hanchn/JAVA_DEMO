package com.example.app.dataaccess;

import com.example.app.entity.Todo;

import java.util.List;
import java.util.Optional;

/**
 * Todo数据访问接口
 * 提供数据访问的抽象层，便于测试和扩展
 */
public interface TodoDataAccess {
    
    /**
     * 保存Todo
     */
    Todo save(Todo todo);
    
    /**
     * 根据ID查找Todo
     */
    Optional<Todo> findById(Long id);
    
    /**
     * 查找所有Todo
     */
    List<Todo> findAll();
    
    /**
     * 根据完成状态查找
     */
    List<Todo> findByCompleted(Boolean completed);
    
    /**
     * 根据优先级查找
     */
    List<Todo> findByPriority(String priority);
    
    /**
     * 根据分类查找
     */
    List<Todo> findByCategory(String category);
    
    /**
     * 根据关键词搜索
     */
    List<Todo> searchByKeyword(String keyword);
    
    /**
     * 获取高优先级未完成任务
     */
    List<Todo> findHighPriorityPendingTodos();
    
    /**
     * 统计已完成任务数量
     */
    long countCompletedTodos();
    
    /**
     * 统计未完成任务数量
     */
    long countPendingTodos();
    
    /**
     * 根据优先级统计
     */
    long countByPriority(String priority);
    
    /**
     * 删除Todo
     */
    void deleteById(Long id);
    
    /**
     * 检查Todo是否存在
     */
    boolean existsById(Long id);
}