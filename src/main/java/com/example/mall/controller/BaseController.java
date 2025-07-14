package com.example.mall.controller;

import com.example.mall.common.ApiResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
public abstract class BaseController {
    
    protected ResponseEntity<ApiResponse<Object>> success(Object data) {
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    protected ResponseEntity<ApiResponse<Object>> success(Object data, String message) {
        return ResponseEntity.ok(ApiResponse.success(data, message));
    }
    
    protected ResponseEntity<ApiResponse<Object>> error(String message) {
        return ResponseEntity.ok(ApiResponse.error(message));
    }
    
    protected ResponseEntity<ApiResponse<Object>> error(int code, String message) {
        return ResponseEntity.ok(ApiResponse.error(code, message));
    }
}