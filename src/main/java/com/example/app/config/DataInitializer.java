package com.example.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.app.entity.Todo;
import com.example.app.repository.TodoRepository;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private TodoRepository todoRepository;

  @Override
  public void run(String... args) throws Exception {
    // 只在数据库为空时初始化数据
    if (todoRepository.count() == 0) {
      initializeSampleData();
    }
  }

  private void initializeSampleData() {
    System.out.println("正在初始化示例数据...");

    // 创建示例待办事项
    Todo todo1 = new Todo("学习Spring Boot", "深入学习Spring Boot框架的核心概念和最佳实践");
    todo1.setPriority("HIGH");
    todo1.setCategory("学习");
    todo1.setCompleted(false);

    Todo todo2 = new Todo("完成项目文档", "编写项目的技术文档和用户手册");
    todo2.setPriority("MEDIUM");
    todo2.setCategory("工作");
    todo2.setCompleted(false);

    Todo todo3 = new Todo("代码审查", "审查团队成员提交的代码");
    todo3.setPriority("HIGH");
    todo3.setCategory("工作");
    todo3.setCompleted(true);

    Todo todo4 = new Todo("健身锻炼", "每周至少3次健身房锻炼");
    todo4.setPriority("LOW");
    todo4.setCategory("健康");
    todo4.setCompleted(false);

    Todo todo5 = new Todo("阅读技术书籍", "阅读《Spring实战》第5版");
    todo5.setPriority("MEDIUM");
    todo5.setCategory("学习");
    todo5.setCompleted(true);

    // 保存到数据库
    todoRepository.save(todo1);
    todoRepository.save(todo2);
    todoRepository.save(todo3);
    todoRepository.save(todo4);
    todoRepository.save(todo5);
    System.out.println("示例数据初始化完成！共创建了 " + todoRepository.count() + " 条记录。");
  }
}