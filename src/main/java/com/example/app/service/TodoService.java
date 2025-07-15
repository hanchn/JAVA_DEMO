package com.example.app.service;

import com.example.app.dataaccess.TodoDataAccess;
import com.example.app.dto.request.TodoCreateRequest;
import com.example.app.dto.request.TodoUpdateRequest;
import com.example.app.dto.response.TodoListResponse;
import com.example.app.dto.response.TodoResponse;
import com.example.app.entity.Todo;
import com.example.app.exception.TodoNotFoundException;
import com.example.app.mapper.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TodoService {
    
    @Autowired
    private TodoDataAccess todoDataAccess;
    
    @Autowired
    private TodoMapper todoMapper;
    
    /**
     * 创建新的Todo
     */
    public TodoResponse createTodo(TodoCreateRequest request) {
        Todo todo = todoMapper.toEntity(request);
        Todo savedTodo = todoDataAccess.save(todo);
        return todoMapper.toResponse(savedTodo);
    }
    
    /**
     * 获取所有Todo
     */
    @Transactional(readOnly = true)
    public TodoListResponse getAllTodos() {
        List<Todo> todos = todoDataAccess.findAll();
        List<TodoResponse> todoResponses = todoMapper.toResponseList(todos);
        
        int totalCount = todos.size();
        int completedCount = (int) todos.stream().filter(Todo::getCompleted).count();
        int pendingCount = totalCount - completedCount;
        
        return new TodoListResponse(todoResponses, totalCount, completedCount, pendingCount);
    }
    
    /**
     * 根据ID获取Todo
     */
    @Transactional(readOnly = true)
    public TodoResponse getTodoById(Long id) {
        Todo todo = todoDataAccess.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        return todoMapper.toResponse(todo);
    }
    
    /**
     * 更新Todo
     */
    public TodoResponse updateTodo(Long id, TodoUpdateRequest request) {
        Todo todo = todoDataAccess.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        
        todoMapper.updateEntity(todo, request);
        Todo updatedTodo = todoDataAccess.save(todo);
        return todoMapper.toResponse(updatedTodo);
    }
    
    /**
     * 删除Todo
     */
    public void deleteTodo(Long id) {
        if (!todoDataAccess.existsById(id)) {
            throw new TodoNotFoundException(id);
        }
        todoDataAccess.deleteById(id);
    }
    
    /**
     * 切换完成状态
     */
    public TodoResponse toggleComplete(Long id) {
        Todo todo = todoDataAccess.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        
        todo.setCompleted(!todo.getCompleted());
        Todo updatedTodo = todoDataAccess.save(todo);
        return todoMapper.toResponse(updatedTodo);
    }
    
    /**
     * 根据完成状态获取Todo
     */
    @Transactional(readOnly = true)
    public List<TodoResponse> getTodosByCompleted(Boolean completed) {
        List<Todo> todos = todoDataAccess.findByCompleted(completed);
        return todoMapper.toResponseList(todos);
    }
    
    /**
     * 搜索Todo
     */
    @Transactional(readOnly = true)
    public List<TodoResponse> searchTodos(String keyword) {
        List<Todo> todos = todoDataAccess.searchByKeyword(keyword);
        return todoMapper.toResponseList(todos);
    }
    
    /**
     * 获取统计信息
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCount", todoDataAccess.findAll().size());
        stats.put("completedCount", todoDataAccess.countCompletedTodos());
        stats.put("pendingCount", todoDataAccess.countPendingTodos());
        stats.put("highPriorityCount", todoDataAccess.countByPriority("HIGH"));
        stats.put("mediumPriorityCount", todoDataAccess.countByPriority("MEDIUM"));
        stats.put("lowPriorityCount", todoDataAccess.countByPriority("LOW"));
        
        return stats;
    }
}