package com.example.todolist.service;

import com.example.todolist.entity.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class TodoService {
    
    @Autowired
    private TodoRepository todoRepository;
    
    /**
     * 获取所有待办事项
     */
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    
    /**
     * 根据ID获取待办事项
     */
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }
    
    /**
     * 创建新的待办事项
     */
    public Todo createTodo(Todo todo) {
        // 设置默认值
        if (todo.getCompleted() == null) {
            todo.setCompleted(false);
        }
        if (todo.getPriority() == null || todo.getPriority().isEmpty()) {
            todo.setPriority("MEDIUM");
        }
        return todoRepository.save(todo);
    }
    
    /**
     * 更新待办事项
     */
    public Todo updateTodo(Long id, Todo todoDetails) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            
            // 更新字段
            if (todoDetails.getTitle() != null) {
                todo.setTitle(todoDetails.getTitle());
            }
            if (todoDetails.getDescription() != null) {
                todo.setDescription(todoDetails.getDescription());
            }
            if (todoDetails.getCompleted() != null) {
                todo.setCompleted(todoDetails.getCompleted());
            }
            if (todoDetails.getPriority() != null) {
                todo.setPriority(todoDetails.getPriority());
            }
            if (todoDetails.getCategory() != null) {
                todo.setCategory(todoDetails.getCategory());
            }
            
            return todoRepository.save(todo);
        }
        throw new RuntimeException("Todo not found with id: " + id);
    }
    
    /**
     * 删除待办事项
     */
    public boolean deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * 切换完成状态
     */
    public Todo toggleComplete(Long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo todo = optionalTodo.get();
            todo.setCompleted(!todo.getCompleted());
            return todoRepository.save(todo);
        }
        throw new RuntimeException("Todo not found with id: " + id);
    }
    
    /**
     * 根据完成状态获取待办事项
     */
    public List<Todo> getTodosByCompleted(Boolean completed) {
        return todoRepository.findByCompleted(completed);
    }
    
    /**
     * 根据优先级获取待办事项
     */
    public List<Todo> getTodosByPriority(String priority) {
        return todoRepository.findByPriority(priority);
    }
    
    /**
     * 根据分类获取待办事项
     */
    public List<Todo> getTodosByCategory(String category) {
        return todoRepository.findByCategory(category);
    }
    
    /**
     * 搜索待办事项
     */
    public List<Todo> searchTodos(String keyword) {
        return todoRepository.searchByKeyword(keyword);
    }
    
    /**
     * 获取高优先级未完成任务
     */
    public List<Todo> getHighPriorityPendingTodos() {
        return todoRepository.findHighPriorityPendingTodos();
    }
    
    /**
     * 批量更新完成状态
     */
    public int batchUpdateCompleted(List<Long> ids, Boolean completed) {
        int count = 0;
        for (Long id : ids) {
            Optional<Todo> optionalTodo = todoRepository.findById(id);
            if (optionalTodo.isPresent()) {
                Todo todo = optionalTodo.get();
                todo.setCompleted(completed);
                todoRepository.save(todo);
                count++;
            }
        }
        return count;
    }
    
    /**
     * 获取统计信息
     */
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalCount = todoRepository.count();
        long completedCount = todoRepository.countCompletedTodos();
        long pendingCount = todoRepository.countPendingTodos();
        
        long highPriorityCount = todoRepository.countByPriority("HIGH");
        long mediumPriorityCount = todoRepository.countByPriority("MEDIUM");
        long lowPriorityCount = todoRepository.countByPriority("LOW");
        
        stats.put("total", totalCount);
        stats.put("completed", completedCount);
        stats.put("pending", pendingCount);
        stats.put("completionRate", totalCount > 0 ? (double) completedCount / totalCount * 100 : 0);
        
        Map<String, Long> priorityStats = new HashMap<>();
        priorityStats.put("high", highPriorityCount);
        priorityStats.put("medium", mediumPriorityCount);
        priorityStats.put("low", lowPriorityCount);
        stats.put("priority", priorityStats);
        
        return stats;
    }
}