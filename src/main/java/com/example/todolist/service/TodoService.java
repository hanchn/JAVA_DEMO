package com.example.todolist.service;

import com.example.todolist.dto.request.TodoCreateRequest;
import com.example.todolist.dto.request.TodoUpdateRequest;
import com.example.todolist.dto.request.TodoQueryRequest;
import com.example.todolist.dto.response.TodoResponse;
import com.example.todolist.dto.response.TodoListResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 待办事项业务服务接口
 */
public interface TodoService {
    
    /**
     * 获取待办事项列表（支持查询和分页）
     */
    TodoListResponse getTodos(TodoQueryRequest queryRequest);
    
    /**
     * 根据ID获取单个待办事项
     */
    Optional<TodoResponse> getTodoById(Integer id);
    
    /**
     * 创建新的待办事项
     */
    TodoResponse createTodo(TodoCreateRequest request);
    
    /**
     * 更新待办事项
     */
    Optional<TodoResponse> updateTodo(Integer id, TodoUpdateRequest request);
    
    /**
     * 删除待办事项
     */
    boolean deleteTodo(Integer id);
    
    /**
     * 批量更新状态
     */
    int batchUpdateStatus(List<Integer> ids, Boolean completed);
    
    /**
     * 获取统计信息
     */
    Map<String, Object> getStats();
    
    /**
     * 同步待办事项到第三方服务
     */
    boolean syncToExternalService(Integer todoId);
    
    /**
     * 从第三方服务导入待办事项
     */
    List<TodoResponse> importFromExternalService(String userId);
}