package com.example.todolist.controller;

import com.example.todolist.service.DatabaseHealthService;
import com.example.todolist.service.LocalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/database")
@CrossOrigin(origins = "*")
public class DatabaseController {

  @Autowired
  private DatabaseHealthService databaseHealthService;

  @Autowired
  private LocalDataService localDataService;

  /**
   * 获取数据库健康状态
   */
  @GetMapping("/health")
  public ResponseEntity<Map<String, Object>> getDatabaseHealth() {
    Map<String, Object> health = databaseHealthService.getDatabaseStats();
    return ResponseEntity.ok(health);
  }

  /**
   * 测试数据库连接
   */
  @GetMapping("/test")
  public ResponseEntity<Map<String, Object>> testDatabase() {
    boolean connected = databaseHealthService.isDatabaseConnected();
    boolean operationsWork = databaseHealthService.testDatabaseOperations();

    Map<String, Object> result = Map.of(
        "connected", connected,
        "operationsWork", operationsWork,
        "status", (connected && operationsWork) ? "OK" : "ERROR");

    return ResponseEntity.ok(result);
  }

  /**
   * 获取本地数据统计
   */
  @GetMapping("/local/stats")
  public ResponseEntity<Map<String, Object>> getLocalDataStats() {
    long totalCount = localDataService.getLocalTodoCount();
    long completedCount = localDataService.getLocalTodosByCompleted(true).size();
    long pendingCount = localDataService.getLocalTodosByCompleted(false).size();

    Map<String, Object> stats = Map.of(
        "totalTodos", totalCount,
        "completedTodos", completedCount,
        "pendingTodos", pendingCount,
        "completionRate", totalCount > 0 ? (double) completedCount / totalCount * 100 : 0);

    return ResponseEntity.ok(stats);
  }
}