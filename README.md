# TodoList Demo - Spring Boot Project

这是一个基于 Spring Boot 的 TodoList 练手项目。

## 技术栈

- Java 17
- Spring Boot 3.2.0
- Spring Web
- Spring Data JPA
- H2 Database (内存数据库)
- Maven

## 项目结构

```
todolist-demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/todolist/
│   │   │       ├── TodolistDemoApplication.java
│   │   │       └── controller/
│   │   │           └── HelloController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/todolist/
│               └── TodolistDemoApplicationTests.java
├── pom.xml
└── README.md
```

## 运行项目

1. 确保已安装 Java 17 和 Maven
2. 在项目根目录执行：
   ```bash
   mvn spring-boot:run
   ```
3. 访问 http://localhost:8080 查看应用
4. 访问 http://localhost:8080/h2-console 查看 H2 数据库控制台

## 开发建议

接下来可以添加：
- Todo 实体类 (Entity)
- Todo 仓库接口 (Repository)
- Todo 服务类 (Service)
- Todo 控制器 (Controller)
- 前端页面 (Thymeleaf 或 静态页面)

祝你练手愉快！