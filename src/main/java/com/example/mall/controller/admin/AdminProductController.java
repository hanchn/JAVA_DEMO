package com.example.mall.controller.admin;

import com.example.mall.controller.BaseController;
import com.example.mall.dto.request.ProductRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminProductController extends BaseController {
    
    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        // 管理员获取商品列表
        return success(null);
    }
    
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
        // 创建商品
        return success(null, "商品创建成功");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        // 更新商品
        return success(null, "商品更新成功");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        // 删除商品
        return success(null, "商品删除成功");
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateProductStatus(@PathVariable Long id, @RequestParam String status) {
        // 更新商品状态（上架/下架）
        return success(null, "商品状态更新成功");
    }
}