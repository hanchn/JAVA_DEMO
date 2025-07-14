package com.example.todolist.controller;

import com.example.todolist.dto.request.TodoCreateRequest;
import com.example.todolist.dto.request.TodoUpdateRequest;
import com.example.todolist.dto.request.TodoQueryRequest;
import com.example.todolist.dto.response.TodoResponse;
import com.example.todolist.dto.response.TodoListResponse;
import com.example.todolist.dto.response.ApiResponse;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

  // 模拟数据存储 - 使用TodoResponse对象
  private List<TodoResponse> todoList = new ArrayList<>();
  private Integer nextId = 1;

  // 构造函数，初始化一些测试数据
  public TodoController() {
    TodoResponse todo1 = new TodoResponse();
    todo1.setId(nextId++);
    todo1.setTitle("学习 Spring Boot");
    todo1.setDescription("深入学习Spring Boot框架");
    todo1.setCompleted(false);
    todo1.setPriority("HIGH");
    todo1.setCategory("学习");
    todo1.setCreatedAt(LocalDateTime.now());
    todo1.setUpdatedAt(LocalDateTime.now());
    todoList.add(todo1);

    TodoResponse todo2 = new TodoResponse();
    todo2.setId(nextId++);
    todo2.setTitle("完成项目开发");
    todo2.setDescription("完成TodoList项目的开发工作");
    todo2.setCompleted(false);
    todo2.setPriority("MEDIUM");
    todo2.setCategory("工作");
    todo2.setCreatedAt(LocalDateTime.now());
    todo2.setUpdatedAt(LocalDateTime.now());
    todoList.add(todo2);
  }

  // 获取所有待办事项（支持查询参数）
  @GetMapping
  public ResponseEntity<ApiResponse<TodoListResponse>> getTodos(
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) Boolean completed,
      @RequestParam(required = false) String priority,
      @RequestParam(required = false) String category,
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {

    List<TodoResponse> filteredTodos = todoList.stream()
        .filter(todo -> keyword == null ||
            todo.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
            (todo.getDescription() != null && todo.getDescription().toLowerCase().contains(keyword.toLowerCase())))
        .filter(todo -> completed == null || todo.getCompleted().equals(completed))
        .filter(todo -> priority == null || priority.equals(todo.getPriority()))
        .filter(todo -> category == null || category.equals(todo.getCategory()))
        .collect(Collectors.toList());

    // 简单分页
    int start = page * size;
    int end = Math.min(start + size, filteredTodos.size());
    List<TodoResponse> pagedTodos = start < filteredTodos.size() ? filteredTodos.subList(start, end)
        : new ArrayList<>();

    TodoListResponse response = new TodoListResponse(pagedTodos, filteredTodos.size(), page, size);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

  // 根据ID获取单个待办事项
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<TodoResponse>> getTodo(@PathVariable Integer id) {
    Optional<TodoResponse> todo = todoList.stream()
        .filter(t -> t.getId().equals(id))
        .findFirst();

    if (todo.isPresent()) {
      return ResponseEntity.ok(ApiResponse.success(todo.get()));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // 添加新的待办事项
  @PostMapping
  public ResponseEntity<ApiResponse<TodoResponse>> createTodo(@Valid @RequestBody TodoCreateRequest request) {
    TodoResponse newTodo = new TodoResponse();
    newTodo.setId(nextId++);
    newTodo.setTitle(request.getTitle());
    newTodo.setDescription(request.getDescription());
    newTodo.setCompleted(request.getCompleted() != null ? request.getCompleted() : false);
    newTodo.setPriority(request.getPriority());
    newTodo.setCategory(request.getCategory());
    newTodo.setCreatedAt(LocalDateTime.now());
    newTodo.setUpdatedAt(LocalDateTime.now());

    todoList.add(newTodo);
    return ResponseEntity.ok(ApiResponse.success("创建成功", newTodo));
  }

  // 更新待办事项
  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<TodoResponse>> updateTodo(
      @PathVariable Integer id,
      @Valid @RequestBody TodoUpdateRequest request) {

    Optional<TodoResponse> todoOpt = todoList.stream()
        .filter(t -> t.getId().equals(id))
        .findFirst();

    if (todoOpt.isPresent()) {
      TodoResponse todo = todoOpt.get();

      // 只更新非null的字段
      if (request.getTitle() != null) {
        todo.setTitle(request.getTitle());
      }
      if (request.getDescription() != null) {
        todo.setDescription(request.getDescription());
      }
      if (request.getCompleted() != null) {
        todo.setCompleted(request.getCompleted());
      }
      if (request.getPriority() != null) {
        todo.setPriority(request.getPriority());
      }
      if (request.getCategory() != null) {
        todo.setCategory(request.getCategory());
      }

      todo.setUpdatedAt(LocalDateTime.now());

      return ResponseEntity.ok(ApiResponse.success("更新成功", todo));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // 删除待办事项
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<String>> deleteTodo(@PathVariable Integer id) {
    boolean removed = todoList.removeIf(todo -> todo.getId().equals(id));

    if (removed) {
      return ResponseEntity.ok(ApiResponse.success("删除成功"));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // 批量更新待办事项状态
  @PatchMapping("/batch/status")
  public ResponseEntity<ApiResponse<String>> batchUpdateStatus(
      @RequestParam List<Integer> ids,
      @RequestParam Boolean completed) {

    int updatedCount = 0;
    for (Integer id : ids) {
      Optional<TodoResponse> todoOpt = todoList.stream()
          .filter(t -> t.getId().equals(id))
          .findFirst();

      if (todoOpt.isPresent()) {
        todoOpt.get().setCompleted(completed);
        todoOpt.get().setUpdatedAt(LocalDateTime.now());
        updatedCount++;
      }
    }

    return ResponseEntity.ok(ApiResponse.success("批量更新成功，共更新 " + updatedCount + " 条记录"));
  }

  // 获取统计信息
  @GetMapping("/stats")
  public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {
    long totalCount = todoList.size();
    long completedCount = todoList.stream().filter(TodoResponse::getCompleted).count();
    long pendingCount = totalCount - completedCount;

    Map<String, Object> stats = new HashMap<>();
    stats.put("total", totalCount);
    stats.put("completed", completedCount);
    stats.put("pending", pendingCount);
    stats.put("completionRate", totalCount > 0 ? (double) completedCount / totalCount * 100 : 0);

    return ResponseEntity.ok(ApiResponse.success(stats));
  }

  // 健康检查接口
  @GetMapping("/health")
  public ResponseEntity<ApiResponse<String>> health() {
    return ResponseEntity.ok(ApiResponse.success("TodoList API 运行正常"));
  }
}