package com.example.todolist.dto.request;

/**
 * 查询待办事项请求DTO
 */
public class TodoQueryRequest {

  private String keyword;
  private Boolean completed;
  private String priority;
  private String category;
  private Integer page = 0;
  private Integer size = 10;

  // 构造函数
  public TodoQueryRequest() {
  }

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