package com.example.mall.controller.user;

import com.example.mall.controller.BaseController;
import com.example.mall.dto.request.UserRegisterRequest;
import com.example.mall.dto.request.UserLoginRequest;
import com.example.mall.dto.request.UserProfileRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest request) {
        // 用户注册逻辑
        return success(null, "注册成功");
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        // 用户登录逻辑
        return success(null, "登录成功");
    }
    
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        // 获取用户信息
        return success(null);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileRequest request) {
        // 更新用户信息
        return success(null, "更新成功");
    }
}