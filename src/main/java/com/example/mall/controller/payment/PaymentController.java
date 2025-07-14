package com.example.mall.controller.payment;

import com.example.mall.controller.BaseController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController extends BaseController {
    
    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest request) {
        // 创建支付订单
        return success(null, "支付订单创建成功");
    }
    
    @PostMapping("/alipay/notify")
    public String alipayNotify(@RequestBody String notifyData) {
        // 支付宝支付回调
        return "success";
    }
    
    @PostMapping("/wechat/notify")
    public String wechatNotify(@RequestBody String notifyData) {
        // 微信支付回调
        return "success";
    }
    
    @GetMapping("/status/{orderNo}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String orderNo) {
        // 查询支付状态
        return success(null);
    }
}