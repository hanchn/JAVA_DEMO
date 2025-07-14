package com.example.todolist.dto.response;

/**
 * 通用API响应DTO
 */
public class ApiResponse<T> {

  private Integer code;
  private String message;
  private T data;
  private Long timestamp;

  // 构造函数
  public ApiResponse() {
    this.timestamp = System.currentTimeMillis();
  }

  public ApiResponse(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
    this.timestamp = System.currentTimeMillis();
  }

  // 静态工厂方法
  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(200, "操作成功", data);
  }

  public static <T> ApiResponse<T> success(String message, T data) {
    return new ApiResponse<>(200, message, data);
  }

  public static <T> ApiResponse<T> error(String message) {
    return new ApiResponse<>(500, message, null);
  }

  public static <T> ApiResponse<T> error(Integer code, String message) {
    return new ApiResponse<>(code, message, null);
  }

  // Getter和Setter方法
  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }
}