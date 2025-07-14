package com.example.todolist.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TodoController {

  // 模拟数据存储
  private List<Map<String, Object>> todoList = new ArrayList<>();

  // 构造函数，初始化一些测试数据
  public TodoController() {
    Map<String, Object> todo1 = new HashMap<>();
    todo1.put("id", 1);
    todo1.put("title", "学习 Spring Boot");
    todo1.put("completed", false);
    todoList.add(todo1);

    Map<String, Object> todo2 = new HashMap<>();
    todo2.put("id", 2);
    todo2.put("title", "完成项目开发");
    todo2.put("completed", false);
    todoList.add(todo2);
  }

  // 获取所有待办事项
  @GetMapping("/getList")
  public ResponseEntity<List<Map<String, Object>>> getList() {
    return ResponseEntity.ok(todoList);
  }

  // 根据ID获取单个待办事项
  @GetMapping("/getTodo/{id}")
  public ResponseEntity<Map<String, Object>> getTodo(@PathVariable Integer id) {
    for (Map<String, Object> todo : todoList) {
      if (todo.get("id").equals(id)) {
        return ResponseEntity.ok(todo);
      }
    }
    return ResponseEntity.notFound().build();
  }

  // 添加新的待办事项
  @PostMapping("/addTodo")
  public ResponseEntity<Map<String, Object>> addTodo(@RequestBody Map<String, Object> newTodo) {
    // 生成新的ID
    int newId = todoList.size() + 1;
    newTodo.put("id", newId);
    newTodo.put("completed", false);

    todoList.add(newTodo);
    return ResponseEntity.ok(newTodo);
  }

  // 更新待办事项状态
  @PutMapping("/updateTodo/{id}")
  public ResponseEntity<Map<String, Object>> updateTodo(@PathVariable Integer id,
      @RequestBody Map<String, Object> updates) {
    for (Map<String, Object> todo : todoList) {
      if (todo.get("id").equals(id)) {
        todo.putAll(updates);
        return ResponseEntity.ok(todo);
      }
    }
    return ResponseEntity.notFound().build();
  }

  // 删除待办事项
  @DeleteMapping("/deleteTodo/{id}")
  public ResponseEntity<String> deleteTodo(@PathVariable Integer id) {
    todoList.removeIf(todo -> todo.get("id").equals(id));
    return ResponseEntity.ok("删除成功");
  }

  // 简单的健康检查接口
  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("TodoList API 运行正常");
  }
}