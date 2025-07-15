package com.example.todolist.repository;

import com.example.todolist.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  // 根据完成状态查找
  List<Todo> findByCompleted(Boolean completed);

  // 根据标题模糊查询
  List<Todo> findByTitleContainingIgnoreCase(String title);

  // 查找所有未完成的任务，按创建时间排序
  @Query("SELECT t FROM Todo t WHERE t.completed = false ORDER BY t.createdAt DESC")
  List<Todo> findPendingTodos();

  // 查找所有已完成的任务
  @Query("SELECT t FROM Todo t WHERE t.completed = true ORDER BY t.updatedAt DESC")
  List<Todo> findCompletedTodos();
}