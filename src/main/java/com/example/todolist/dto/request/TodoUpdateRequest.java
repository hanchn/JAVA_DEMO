package com.example.todolist.dto.request;

import jakarta.validation.constraints.Size;

/**
 * 更新待办事项请求DTO
 */
public class TodoUpdateRequest {
    
    @Size(max = 200, message = "标题长度不能超过200个字符")
    private String title;
    
    @Size(max = 500, message = "描述长度不能超过500个字符")
    private String description;
    
    private Boolean completed;
    
    private String priority;
    
    private String category;
    
    // 构造函数
    public TodoUpdateRequest() {}
    
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