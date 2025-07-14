package com.example.mall.controller.product;

import com.example.mall.controller.BaseController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController extends BaseController {
    
    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId) {
        // 获取商品列表（分页、搜索、分类筛选）
        return success(null);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long id) {
        // 获取商品详情
        return success(null);
    }
    
    @GetMapping("/hot")
    public ResponseEntity<?> getHotProducts() {
        // 获取热门商品
        return success(null);
    }
    
    @GetMapping("/recommendations")
    public ResponseEntity<?> getRecommendations(@RequestParam(required = false) Long userId) {
        // 获取推荐商品
        return success(null);
    }
}