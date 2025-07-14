package com.example.mall.controller.order;

import com.example.mall.controller.BaseController;
import com.example.mall.dto.request.CreateOrderRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController extends BaseController {
    
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        // 创建订单
        return success(null, "订单创建成功");
    }
    
    @GetMapping
    public ResponseEntity<?> getOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status) {
        // 获取用户订单列表
        return success(null);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long id) {
        // 获取订单详情
        return success(null);
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        // 取消订单
        return success(null, "订单已取消");
    }
    
    @PutMapping("/{id}/confirm")
    public ResponseEntity<?> confirmOrder(@PathVariable Long id) {
        // 确认收货
        return success(null, "订单已确认收货");
    }
}