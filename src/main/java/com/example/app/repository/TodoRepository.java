package com.example.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.app.entity.Todo;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 根据完成状态查找
    List<Todo> findByCompleted(Boolean completed);

    // 根据优先级查找
    List<Todo> findByPriority(String priority);

    // 根据分类查找
    List<Todo> findByCategory(String category);

    // 根据标题模糊查询
    List<Todo> findByTitleContainingIgnoreCase(String title);

    // 根据描述模糊查询
    List<Todo> findByDescriptionContainingIgnoreCase(String description);

    // 复合查询：根据完成状态和优先级
    List<Todo> findByCompletedAndPriority(Boolean completed, String priority);

    // 自定义查询：获取未完成的高优先级任务
    @Query("SELECT t FROM Todo t WHERE t.completed = false AND t.priority = 'HIGH' ORDER BY t.createdAt ASC")
    List<Todo> findHighPriorityPendingTodos();

    // 自定义查询：根据关键词搜索标题或描述
    @Query("SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Todo> searchByKeyword(@Param("keyword") String keyword);

    // 统计查询
    @Query("SELECT COUNT(t) FROM Todo t WHERE t.completed = true")
    long countCompletedTodos();

    @Query("SELECT COUNT(t) FROM Todo t WHERE t.completed = false")
    long countPendingTodos();

    // 按优先级统计
    @Query("SELECT COUNT(t) FROM Todo t WHERE t.priority = :priority")
    long countByPriority(@Param("priority") String priority);
}