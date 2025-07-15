package com.example.todolist.controller;

import com.example.todolist.entity.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

  @Autowired
  private TodoService todoService;

  // 获取所有待办事项
  @GetMapping
  public ResponseEntity<List<Todo>> getAllTodos() {
    List<Todo> todos = todoService.getAllTodos();
    return ResponseEntity.ok(todos);
  }

  // 根据ID获取待办事项
  @GetMapping("/{id}")
  public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
    Optional<Todo> todo = todoService.getTodoById(id);
    return todo.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // 创建新的待办事项
  @PostMapping
  public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
    Todo createdTodo = todoService.createTodo(todo);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
  }

  // 更新待办事项
  @PutMapping("/{id}")
  public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
    Todo updatedTodo = todoService.updateTodo(id, todoDetails);
    if (updatedTodo != null) {
      return ResponseEntity.ok(updatedTodo);
    }
    return ResponseEntity.notFound().build();
  }

  // 删除待办事项
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
    boolean deleted = todoService.deleteTodo(id);
    if (deleted) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  // 切换完成状态
  @PatchMapping("/{id}/toggle")
  public ResponseEntity<Todo> toggleComplete(@PathVariable Long id) {
    Todo updatedTodo = todoService.toggleComplete(id);
    if (updatedTodo != null) {
      return ResponseEntity.ok(updatedTodo);
    }
    return ResponseEntity.notFound().build();
  }

  // 获取未完成的任务
  @GetMapping("/pending")
  public ResponseEntity<List<Todo>> getPendingTodos() {
    List<Todo> pendingTodos = todoService.getPendingTodos();
    return ResponseEntity.ok(pendingTodos);
  }

  // 获取已完成的任务
  @GetMapping("/completed")
  public ResponseEntity<List<Todo>> getCompletedTodos() {
    List<Todo> completedTodos = todoService.getCompletedTodos();
    return ResponseEntity.ok(completedTodos);
  }

  // 搜索待办事项
  @GetMapping("/search")
  public ResponseEntity<List<Todo>> searchTodos(@RequestParam String keyword) {
    List<Todo> todos = todoService.searchTodos(keyword);
    return ResponseEntity.ok(todos);
  }
}