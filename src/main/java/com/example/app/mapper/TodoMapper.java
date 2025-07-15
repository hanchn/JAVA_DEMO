package com.example.app.mapper;

import com.example.app.dto.request.TodoCreateRequest;
import com.example.app.dto.request.TodoUpdateRequest;
import com.example.app.dto.response.TodoResponse;
import com.example.app.entity.Todo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoMapper {

  /**
   * 创建请求DTO转换为Entity
   */
  public Todo toEntity(TodoCreateRequest request) {
    if (request == null) {
      return null;
    }

    Todo todo = new Todo();
    todo.setTitle(request.getTitle());
    todo.setDescription(request.getDescription());
    todo.setPriority(request.getPriority());
    todo.setCategory(request.getCategory());
    todo.setCompleted(false);

    return todo;
  }

  /**
   * Entity转换为响应DTO
   */
  public TodoResponse toResponse(Todo todo) {
    if (todo == null) {
      return null;
    }

    return new TodoResponse(
        todo.getId(),
        todo.getTitle(),
        todo.getDescription(),
        todo.getCompleted(),
        todo.getPriority(),
        todo.getCategory(),
        todo.getCreatedAt(),
        todo.getUpdatedAt());
  }

  /**
   * Entity列表转换为响应DTO列表
   */
  public List<TodoResponse> toResponseList(List<Todo> todos) {
    if (todos == null) {
      return null;
    }

    return todos.stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }

  /**
   * 更新请求DTO应用到Entity
   */
  public void updateEntity(Todo todo, TodoUpdateRequest request) {
    if (todo == null || request == null) {
      return;
    }

    if (request.getTitle() != null) {
      todo.setTitle(request.getTitle());
    }
    if (request.getDescription() != null) {
      todo.setDescription(request.getDescription());
    }
    if (request.getPriority() != null) {
      todo.setPriority(request.getPriority());
    }
    if (request.getCategory() != null) {
      todo.setCategory(request.getCategory());
    }
    if (request.getCompleted() != null) {
      todo.setCompleted(request.getCompleted());
    }

    todo.setUpdatedAt(LocalDateTime.now());
  }
}