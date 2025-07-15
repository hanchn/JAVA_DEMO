package com.example.todolist.config;

import com.example.todolist.entity.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private TodoRepository todoRepository;

  @Override
  public void run(String... args) throws Exception {
    // 检查数据库是否为空，如果为空则初始化测试数据
    if (todoRepository.count() == 0) {
      initializeTestData();
    }
  }

  private void initializeTestData() {
    System.out.println("正在初始化测试数据...");

    // 创建一些示例待办事项
    Todo todo1 = new Todo();
    todo1.setTitle("学习Spring Boot");
    todo1.setDescription("深入学习Spring Boot框架，掌握核心概念");
    todo1.setCompleted(false);
    todo1.setCreatedAt(LocalDateTime.now().minusDays(2));
    todo1.setUpdatedAt(LocalDateTime.now().minusDays(2));

    Todo todo2 = new Todo();
    todo2.setTitle("完成TodoList项目");
    todo2.setDescription("实现一个完整的TodoList应用，包括CRUD功能");
    todo2.setCompleted(false);
    todo2.setCreatedAt(LocalDateTime.now().minusDays(1));
    todo2.setUpdatedAt(LocalDateTime.now().minusDays(1));

    Todo todo3 = new Todo();
    todo3.setTitle("阅读技术文档");
    todo3.setDescription("阅读Spring官方文档");
    todo3.setCompleted(true);
    todo3.setCreatedAt(LocalDateTime.now().minusDays(3));
    todo3.setUpdatedAt(LocalDateTime.now().minusHours(5));

    Todo todo4 = new Todo();
    todo4.setTitle("代码重构");
    todo4.setDescription("重构现有代码，提高代码质量");
    todo4.setCompleted(false);
    todo4.setCreatedAt(LocalDateTime.now().minusHours(2));
    todo4.setUpdatedAt(LocalDateTime.now().minusHours(2));

    Todo todo5 = new Todo();
    todo5.setTitle("写单元测试");
    todo5.setDescription("为TodoService编写单元测试");
    todo5.setCompleted(true);
    todo5.setCreatedAt(LocalDateTime.now().minusDays(4));
    todo5.setUpdatedAt(LocalDateTime.now().minusDays(1));

    // 保存到数据库
    todoRepository.save(todo1);
    todoRepository.save(todo2);
    todoRepository.save(todo3);
    todoRepository.save(todo4);
    todoRepository.save(todo5);

    System.out.println("测试数据初始化完成！共创建了 " + todoRepository.count() + " 条记录。");
  }
}