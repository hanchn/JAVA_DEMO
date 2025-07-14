package com.example.mall.controller.cart;

import com.example.mall.controller.BaseController;
import com.example.mall.dto.request.CartItemRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController extends BaseController {
    
    @GetMapping
    public ResponseEntity<?> getCartItems() {
        // 获取购物车商品
        return success(null);
    }
    
    @PostMapping("/items")
    public ResponseEntity<?> addToCart(@RequestBody CartItemRequest request) {
        // 添加商品到购物车
        return success(null, "商品已添加到购物车");
    }
    
    @PutMapping("/items/{id}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long id, @RequestBody CartItemRequest request) {
        // 更新购物车商品数量
        return success(null, "购物车已更新");
    }
    
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long id) {
        // 从购物车移除商品
        return success(null, "商品已从购物车移除");
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        // 清空购物车
        return success(null, "购物车已清空");
    }
}