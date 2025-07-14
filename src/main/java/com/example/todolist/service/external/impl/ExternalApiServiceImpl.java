package com.example.todolist.service.external.impl;

import com.example.todolist.dto.response.TodoResponse;
import com.example.todolist.service.external.ExternalApiService;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {
    
    private static final Logger logger = LoggerFactory.getLogger(ExternalApiServiceImpl.class);
    
    @Override
    public boolean syncTodo(TodoResponse todo) {
        logger.info("同步待办事项到第三方服务: {}", todo.getTitle());
        
        try {
            // 模拟调用第三方API
            // 实际项目中这里会使用 RestTemplate 或 WebClient 调用真实的API
            Thread.sleep(100); // 模拟网络延迟
            
            // 模拟成功率90%
            boolean success = Math.random() > 0.1;
            
            if (success) {
                logger.info("成功同步待办事项到第三方服务: {}", todo.getId());
            } else {
                logger.warn("同步待办事项到第三方服务失败: {}", todo.getId());
            }
            
            return success;
            
        } catch (Exception e) {
            logger.error("同步待办事项到第三方服务异常: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public List<TodoResponse> getTodosByUserId(String userId) {
        logger.info("从第三方服务获取用户待办事项: {}", userId);
        
        try {
            // 模拟调用第三方API
            Thread.sleep(200); // 模拟网络延迟
            
            // 模拟返回一些数据
            List<TodoResponse> externalTodos = new ArrayList<>();
            
            TodoResponse todo1 = new TodoResponse();
            todo1.setTitle("来自第三方的任务1");
            todo1.setDescription("这是从第三方服务导入的任务");
            todo1.setCompleted(false);
            todo1.setPriority("LOW");
            todo1.setCategory("导入");
            externalTodos.add(todo1);
            
            TodoResponse todo2 = new TodoResponse();
            todo2.setTitle("来自第三方的任务2");
            todo2.setDescription("另一个从第三方服务导入的任务");
            todo2.setCompleted(true);
            todo2.setPriority("MEDIUM");
            todo2.setCategory("导入");
            externalTodos.add(todo2);
            
            logger.info("成功从第三方服务获取到 {} 个待办事项", externalTodos.size());
            return externalTodos;
            
        } catch (Exception e) {
            logger.error("从第三方服务获取待办事项异常: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @Override
    public boolean sendNotification(String message, String userId) {
        logger.info("发送通知到第三方服务，用户: {}, 消息: {}", userId, message);
        
        try {
            // 模拟发送通知
            Thread.sleep(50);
            
            boolean success = Math.random() > 0.05; // 95%成功率
            
            if (success) {
                logger.info("成功发送通知到第三方服务");
            } else {
                logger.warn("发送通知到第三方服务失败");
            }
            
            return success;
            
        } catch (Exception e) {
            logger.error("发送通知到第三方服务异常: {}", e.getMessage());
            return false;
        }
    }
}