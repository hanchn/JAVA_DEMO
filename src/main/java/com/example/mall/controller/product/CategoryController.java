package com.example.mall.controller.product;

import com.example.mall.controller.BaseController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController extends BaseController {
    
    @GetMapping
    public ResponseEntity<?> getCategories() {
        // 获取分类树
        return success(null);
    }
    
    @GetMapping("/{id}/products")
    public ResponseEntity<?> getProductsByCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        // 根据分类获取商品
        return success(null);
    }
}