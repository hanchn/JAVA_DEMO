package com.example.todolist.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 创建待办事项请求DTO
 */
public class TodoCreateRequest {
    
    @NotBlank(message = "标题不能为空")
    @Size(max = 200, message = "标题长度不能超过200个字符")
    private String title;
    
    @Size(max = 500, message = "描述长度不能超过500个字符")
    private String description;
    
    private Boolean completed = false;
    
    private String priority; // HIGH, MEDIUM, LOW
    
    private String category;
    
    // 构造函数
    public TodoCreateRequest() {}
    
    public TodoCreateRequest(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }
    
    // Getter和Setter方法
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
}