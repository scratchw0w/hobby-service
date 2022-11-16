package com.scratchy.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExceptionInfo {

  private String message;
  private LocalDateTime dateTime;
}
