package com.example.app.dto.request;

import jakarta.validation.constraints.Size;

public class TodoUpdateRequest {
    
    @Size(max = 200, message = "标题长度不能超过200个字符")
    private String title;
    
    @Size(max = 1000, message = "描述长度不能超过1000个字符")
    private String description;
    
    private String priority;
    
    private String category;
    
    private Boolean completed;
    
    // 构造函数
    public TodoUpdateRequest() {}
    
    // Getters and Setters
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
    
    public Boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}