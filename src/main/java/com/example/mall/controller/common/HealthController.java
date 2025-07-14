package com.example.mall.controller.common;

import com.example.mall.controller.BaseController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController extends BaseController {
    
    @GetMapping
    public ResponseEntity<?> health() {
        return success("Mall API 运行正常");
    }
    
    @GetMapping("/version")
    public ResponseEntity<?> version() {
        return success("v1.0.0");
    }
}