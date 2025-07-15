package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.app.entity.Todo;
import com.example.app.service.TodoService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;

    /**
     * 获取所有待办事项
     */
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        System.err.println("获取所有待办事项");
        return ResponseEntity.ok(todos);
    }

    /**
     * 测试接口
     */
    @GetMapping("/test")
    public String test() {
        System.err.println("测试接口");
        return "[{code: 0, msg: \"success\"}]";
    }

    /**
     * 根据ID获取待办事项
     */
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Optional<Todo> todo = todoService.getTodoById(id);
        System.err.println("根据ID获取待办事项" + id);
        return todo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 创建新的待办事项
     */
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        try {
            Todo createdTodo = todoService.createTodo(todo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新待办事项
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        try {
            Todo updatedTodo = todoService.updateTodo(id, todoDetails);
            return ResponseEntity.ok(updatedTodo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 删除待办事项
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        boolean deleted = todoService.deleteTodo(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * 切换完成状态
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggleComplete(@PathVariable Long id) {
        try {
            Todo updatedTodo = todoService.toggleComplete(id);
            return ResponseEntity.ok(updatedTodo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据完成状态获取待办事项
     */
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Todo>> getTodosByCompleted(@PathVariable Boolean completed) {
        List<Todo> todos = todoService.getTodosByCompleted(completed);
        return ResponseEntity.ok(todos);
    }

    /**
     * 根据优先级获取待办事项
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Todo>> getTodosByPriority(@PathVariable String priority) {
        List<Todo> todos = todoService.getTodosByPriority(priority.toUpperCase());
        return ResponseEntity.ok(todos);
    }

    /**
     * 根据分类获取待办事项
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Todo>> getTodosByCategory(@PathVariable String category) {
        List<Todo> todos = todoService.getTodosByCategory(category);
        return ResponseEntity.ok(todos);
    }

    /**
     * 搜索待办事项
     */
    @GetMapping("/search")
    public ResponseEntity<List<Todo>> searchTodos(@RequestParam String keyword) {
        List<Todo> todos = todoService.searchTodos(keyword);
        return ResponseEntity.ok(todos);
    }

    /**
     * 获取高优先级未完成任务
     */
    @GetMapping("/high-priority")
    public ResponseEntity<List<Todo>> getHighPriorityPendingTodos() {
        List<Todo> todos = todoService.getHighPriorityPendingTodos();
        return ResponseEntity.ok(todos);
    }

    /**
     * 批量更新完成状态
     */
    @PatchMapping("/batch/status")
    public ResponseEntity<Map<String, Object>> batchUpdateCompleted(
            @RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<Long> ids = (List<Long>) request.get("ids");
            Boolean completed = (Boolean) request.get("completed");

            int updatedCount = todoService.batchUpdateCompleted(ids, completed);

            Map<String, Object> response = Map.of(
                    "success", true,
                    "updatedCount", updatedCount,
                    "message", "批量更新成功");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = Map.of(
                    "success", false,
                    "message", "批量更新失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
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