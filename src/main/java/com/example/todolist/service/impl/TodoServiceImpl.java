package com.example.todolist.service.impl;

import com.example.todolist.dto.request.TodoCreateRequest;
import com.example.todolist.dto.request.TodoUpdateRequest;
import com.example.todolist.dto.request.TodoQueryRequest;
import com.example.todolist.dto.response.TodoResponse;
import com.example.todolist.dto.response.TodoListResponse;
import com.example.todolist.service.TodoService;
import com.example.todolist.service.repository.TodoRepository;
import com.example.todolist.service.external.ExternalApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {
    
    private static final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);
    
    @Autowired
    private TodoRepository todoRepository;
    
    @Autowired
    private ExternalApiService externalApiService;
    
    @Override
    public TodoListResponse getTodos(TodoQueryRequest queryRequest) {
        logger.info("获取待办事项列表，查询参数: {}", queryRequest);
        
        List<TodoResponse> allTodos = todoRepository.findAll();
        
        // 应用过滤条件
        List<TodoResponse> filteredTodos = allTodos.stream()
            .filter(todo -> queryRequest.getKeyword() == null || 
                todo.getTitle().toLowerCase().contains(queryRequest.getKeyword().toLowerCase()) ||
                (todo.getDescription() != null && todo.getDescription().toLowerCase().contains(queryRequest.getKeyword().toLowerCase())))
            .filter(todo -> queryRequest.getCompleted() == null || todo.getCompleted().equals(queryRequest.getCompleted()))
            .filter(todo -> queryRequest.getPriority() == null || queryRequest.getPriority().equals(todo.getPriority()))
            .filter(todo -> queryRequest.getCategory() == null || queryRequest.getCategory().equals(todo.getCategory()))
            .collect(Collectors.toList());
        
        // 分页处理
        int page = queryRequest.getPage() != null ? queryRequest.getPage() : 0;
        int size = queryRequest.getSize() != null ? queryRequest.getSize() : 10;
        int start = page * size;
        int end = Math.min(start + size, filteredTodos.size());
        
        List<TodoResponse> pagedTodos = start < filteredTodos.size() ? 
            filteredTodos.subList(start, end) : new ArrayList<>();
        
        return new TodoListResponse(pagedTodos, filteredTodos.size(), page, size);
    }
    
    @Override
    public Optional<TodoResponse> getTodoById(Integer id) {
        logger.info("根据ID获取待办事项: {}", id);
        return todoRepository.findById(id);
    }
    
    @Override
    public TodoResponse createTodo(TodoCreateRequest request) {
        logger.info("创建新的待办事项: {}", request.getTitle());
        
        TodoResponse newTodo = new TodoResponse();
        newTodo.setTitle(request.getTitle());
        newTodo.setDescription(request.getDescription());
        newTodo.setCompleted(request.getCompleted() != null ? request.getCompleted() : false);
        newTodo.setPriority(request.getPriority());
        newTodo.setCategory(request.getCategory());
        newTodo.setCreatedAt(LocalDateTime.now());
        newTodo.setUpdatedAt(LocalDateTime.now());
        
        TodoResponse savedTodo = todoRepository.save(newTodo);
        
        // 异步同步到第三方服务
        try {
            syncToExternalService(savedTodo.getId());
        } catch (Exception e) {
            logger.warn("同步到第三方服务失败: {}", e.getMessage());
        }
        
        return savedTodo;
    }
    
    @Override
    public Optional<TodoResponse> updateTodo(Integer id, TodoUpdateRequest request) {
        logger.info("更新待办事项: {}", id);
        
        Optional<TodoResponse> todoOpt = todoRepository.findById(id);
        if (todoOpt.isPresent()) {
            TodoResponse todo = todoOpt.get();
            
            // 只更新非null的字段
            if (request.getTitle() != null) {
                todo.setTitle(request.getTitle());
            }
            if (request.getDescription() != null) {
                todo.setDescription(request.getDescription());
            }
            if (request.getCompleted() != null) {
                todo.setCompleted(request.getCompleted());
            }
            if (request.getPriority() != null) {
                todo.setPriority(request.getPriority());
            }
            if (request.getCategory() != null) {
                todo.setCategory(request.getCategory());
            }
            
            todo.setUpdatedAt(LocalDateTime.now());
            
            TodoResponse updatedTodo = todoRepository.save(todo);
            
            // 异步同步到第三方服务
            try {
                syncToExternalService(updatedTodo.getId());
            } catch (Exception e) {
                logger.warn("同步到第三方服务失败: {}", e.getMessage());
            }
            
            return Optional.of(updatedTodo);
        }
        
        return Optional.empty();
    }
    
    @Override
    public boolean deleteTodo(Integer id) {
        logger.info("删除待办事项: {}", id);
        return todoRepository.deleteById(id);
    }
    
    @Override
    public int batchUpdateStatus(List<Integer> ids, Boolean completed) {
        logger.info("批量更新状态，IDs: {}, 状态: {}", ids, completed);
        
        int updatedCount = 0;
        for (Integer id : ids) {
            Optional<TodoResponse> todoOpt = todoRepository.findById(id);
            if (todoOpt.isPresent()) {
                TodoResponse todo = todoOpt.get();
                todo.setCompleted(completed);
                todo.setUpdatedAt(LocalDateTime.now());
                todoRepository.save(todo);
                updatedCount++;
            }
        }
        
        return updatedCount;
    }
    
    @Override
    public Map<String, Object> getStats() {
        logger.info("获取统计信息");
        
        List<TodoResponse> allTodos = todoRepository.findAll();
        long totalCount = allTodos.size();
        long completedCount = allTodos.stream().filter(TodoResponse::getCompleted).count();
        long pendingCount = totalCount - completedCount;
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", totalCount);
        stats.put("completed", completedCount);
        stats.put("pending", pendingCount);
        stats.put("completionRate", totalCount > 0 ? (double) completedCount / totalCount * 100 : 0);
        
        return stats;
    }
    
    @Override
    public boolean syncToExternalService(Integer todoId) {
        logger.info("同步待办事项到第三方服务: {}", todoId);
        
        Optional<TodoResponse> todoOpt = todoRepository.findById(todoId);
        if (todoOpt.isPresent()) {
            return externalApiService.syncTodo(todoOpt.get());
        }
        
        return false;
    }
    
    @Override
    public List<TodoResponse> importFromExternalService(String userId) {
        logger.info("从第三方服务导入待办事项，用户ID: {}", userId);
        
        List<TodoResponse> externalTodos = externalApiService.getTodosByUserId(userId);
        
        // 保存到本地数据库
        List<TodoResponse> savedTodos = new ArrayList<>();
        for (TodoResponse todo : externalTodos) {
            todo.setCreatedAt(LocalDateTime.now());
            todo.setUpdatedAt(LocalDateTime.now());
            savedTodos.add(todoRepository.save(todo));
        }
        
        return savedTodos;
    }
}