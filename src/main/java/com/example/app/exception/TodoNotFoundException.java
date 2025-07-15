package com.example.app.exception;

public class TodoNotFoundException extends RuntimeException {

  public TodoNotFoundException(String message) {
    super(message);
  }

  public TodoNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public TodoNotFoundException(Long id) {
    super("Todo not found with id: " + id);
  }
}