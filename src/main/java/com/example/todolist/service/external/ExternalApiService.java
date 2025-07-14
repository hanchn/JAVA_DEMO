package com.example.todolist.service.external;

import com.example.todolist.dto.response.TodoResponse;

import java.util.List;

/**
 * 第三方接口服务
 */
public interface ExternalApiService {
    
    /**
     * 同步待办事项到第三方服务
     */
    boolean syncTodo(TodoResponse todo);
    
    /**
     * 从第三方服务获取用户的待办事项
     */
    List<TodoResponse> getTodosByUserId(String userId);
    
    /**
     * 发送通知到第三方服务
     */
    boolean sendNotification(String message, String userId);
}