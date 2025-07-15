package com.example.todolist.service;

import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DatabaseHealthService {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private TodoRepository todoRepository;

  /**
   * 检查数据库连接状态
   */
  public boolean isDatabaseConnected() {
    try (Connection connection = dataSource.getConnection()) {
      return connection.isValid(5); // 5秒超时
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * 获取数据库统计信息
   */
  public Map<String, Object> getDatabaseStats() {
    Map<String, Object> stats = new HashMap<>();

    try {
      long totalCount = todoRepository.count();
      long completedCount = todoRepository.findByCompleted(true).size();
      long pendingCount = todoRepository.findByCompleted(false).size();

      stats.put("connected", isDatabaseConnected());
      stats.put("totalTodos", totalCount);
      stats.put("completedTodos", completedCount);
      stats.put("pendingTodos", pendingCount);
      stats.put("completionRate", totalCount > 0 ? (double) completedCount / totalCount * 100 : 0);

    } catch (Exception e) {
      stats.put("connected", false);
      stats.put("error", e.getMessage());
    }

    return stats;
  }

  /**
   * 测试数据库操作
   */
  public boolean testDatabaseOperations() {
    try {
      // 测试查询操作
      todoRepository.count();
      todoRepository.findAll();
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}