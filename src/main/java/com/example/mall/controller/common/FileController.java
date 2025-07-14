package com.example.mall.controller.common;

import com.example.mall.controller.BaseController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/v1/files")
public class FileController extends BaseController {
    
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        // 文件上传
        return success(null, "文件上传成功");
    }
    
    @PostMapping("/upload/batch")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        // 批量文件上传
        return success(null, "文件批量上传成功");
    }
}