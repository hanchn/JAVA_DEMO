package com.example.app.dto.response;

import java.util.List;

public class TodoListResponse {
    
    private List<TodoResponse> todos;
    private int totalCount;
    private int completedCount;
    private int pendingCount;
    
    // 构造函数
    public TodoListResponse() {}
    
    public TodoListResponse(List<TodoResponse> todos, int totalCount, int completedCount, int pendingCount) {
        this.todos = todos;
        this.totalCount = totalCount;
        this.completedCount = completedCount;
        this.pendingCount = pendingCount;
    }
    
    // Getters and Setters
    public List<TodoResponse> getTodos() {
        return todos;
    }
    
    public void setTodos(List<TodoResponse> todos) {
        this.todos = todos;
    }
    
    public int getTotalCount() {
        return totalCount;
    }
    
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
    public int getCompletedCount() {
        return completedCount;
    }
    
    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }
    
    public int getPendingCount() {
        return pendingCount;
    }
    
    public void setPendingCount(int pendingCount) {
        this.pendingCount = pendingCount;
    }
}