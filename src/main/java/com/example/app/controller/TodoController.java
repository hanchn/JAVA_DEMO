package com.example.app.controller;

import com.example.app.dto.request.TodoCreateRequest;
import com.example.app.dto.request.TodoUpdateRequest;
import com.example.app.dto.response.TodoListResponse;
import com.example.app.dto.response.TodoResponse;
import com.example.app.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    /**
     * 创建新的Todo
     */
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoCreateRequest request) {
        TodoResponse response = todoService.createTodo(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 获取所有Todo（带统计信息）
     */
    @GetMapping
    public ResponseEntity<TodoListResponse> getAllTodos() {
        TodoListResponse response = todoService.getAllTodos();
        return ResponseEntity.ok(response);
    }

    /**
     * 根据ID获取Todo
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        TodoResponse response = todoService.getTodoById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 更新Todo
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id,
            @Valid @RequestBody TodoUpdateRequest request) {
        TodoResponse response = todoService.updateTodo(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 删除Todo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 切换完成状态
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TodoResponse> toggleComplete(@PathVariable Long id) {
        TodoResponse response = todoService.toggleComplete(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 根据完成状态获取Todo
     */
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<TodoResponse>> getTodosByCompleted(@PathVariable Boolean completed) {
        List<TodoResponse> response = todoService.getTodosByCompleted(completed);
        return ResponseEntity.ok(response);
    }

    /**
     * 搜索Todo
     */
    @GetMapping("/search")
    public ResponseEntity<List<TodoResponse>> searchTodos(@RequestParam String keyword) {
        List<TodoResponse> response = todoService.searchTodos(keyword);
        return ResponseEntity.ok(response);
    }

    /**
     * 获取统计信息
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = todoService.getStatistics();
        return ResponseEntity.ok(stats);
    }
}