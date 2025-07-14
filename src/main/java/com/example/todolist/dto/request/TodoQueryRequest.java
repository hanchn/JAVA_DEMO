package com.example.todolist.dto.request;

/**
 * 查询待办事项请求DTO
 */
public class TodoQueryRequest {
    
    private String keyword; // 搜索关键词
    private Boolean completed; // 完成状态筛选
    private String priority; // 优先级筛选
    private String category; // 分类筛选
    private String sortBy = "id"; // 排序字段
    private String sortDirection = "ASC"; // 排序方向
    private Integer page = 0; // 页码
    private Integer size = 10; // 每页大小
    
    // 构造函数
    public TodoQueryRequest() {}
    
    // Getter和Setter方法
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortDirection() {
        return sortDirection;
    }
    
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
    
    public Integer getPage() {
        return page;
    }
    
    public void setPage(Integer page) {
        this.page = page;
    }
    
    public Integer getSize() {
        return size;
    }
    
    public void setSize(Integer size) {
        this.size = size;
    }
}