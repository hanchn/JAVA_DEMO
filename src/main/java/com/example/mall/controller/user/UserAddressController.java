package com.example.mall.controller.user;

import com.example.mall.controller.BaseController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/users/addresses")
public class UserAddressController extends BaseController {
    
    @GetMapping
    public ResponseEntity<?> getAddresses() {
        // 获取用户地址列表
        return success(null);
    }
    
    @PostMapping
    public ResponseEntity<?> addAddress(@RequestBody AddressRequest request) {
        // 添加地址
        return success(null, "地址添加成功");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody AddressRequest request) {
        // 更新地址
        return success(null, "地址更新成功");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        // 删除地址
        return success(null, "地址删除成功");
    }
}