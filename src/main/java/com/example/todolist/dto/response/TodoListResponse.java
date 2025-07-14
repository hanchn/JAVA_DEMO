package com.example.todolist.dto.response;

import java.util.List;

/**
 * 待办事项列表响应DTO
 */
public class TodoListResponse {

  private List<TodoResponse> todos;
  private Integer total;
  private Integer page;
  private Integer size;
  private Integer totalPages;

  // 构造函数
  public TodoListResponse() {
  }

  public TodoListResponse(List<TodoResponse> todos, Integer total, Integer page, Integer size) {
    this.todos = todos;
    this.total = total;
    this.page = page;
    this.size = size;
    this.totalPages = (int) Math.ceil((double) total / size);
  }

  // Getter和Setter方法
  public List<TodoResponse> getTodos() {
    return todos;
  }

  public void setTodos(List<TodoResponse> todos) {
    this.todos = todos;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
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

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }
}