package com.pablobuendia.notificationservice.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

  private final Integer status;
  private final String message;
  private final LocalDateTime dateTime;
  private String trace;
  private String path;
}
