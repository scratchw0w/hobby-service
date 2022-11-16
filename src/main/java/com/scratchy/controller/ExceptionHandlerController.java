package com.scratchy.controller;

import java.time.LocalDateTime;

import com.scratchy.entity.ExceptionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(value = {IllegalArgumentException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionInfo illegalArgumentException(IllegalArgumentException ex) {
    log.debug("Handling illegal argument exception {}", ex.getMessage());
    return ExceptionInfo.builder()
        .message(ex.getMessage())
        .dateTime(LocalDateTime.now())
        .build();
  }
}
