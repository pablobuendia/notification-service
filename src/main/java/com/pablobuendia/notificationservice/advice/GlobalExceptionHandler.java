package com.pablobuendia.notificationservice.advice;

import com.pablobuendia.notificationservice.notifier.exception.TypeNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(TypeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ExceptionResponse handleNotFoundExceptions(TypeNotFoundException exception,
      HttpServletRequest httpServletRequest) {
    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.NOT_FOUND.value(), exception.getMessage(), LocalDateTime.now()
    );

    response.setPath(httpServletRequest.getRequestURI());
    return response;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ExceptionResponse handleNotFoundExceptions(IllegalArgumentException exception,
      HttpServletRequest httpServletRequest) {
    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.BAD_REQUEST.value(), exception.getMessage(), LocalDateTime.now()
    );

    response.setPath(httpServletRequest.getRequestURI());
    return response;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ExceptionResponse onConstraintValidationException(
      ConstraintViolationException exception,
      HttpServletRequest httpServletRequest) {
    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.BAD_REQUEST.value(), exception.getMessage(), LocalDateTime.now()
    );

    response.setPath(httpServletRequest.getRequestURI());
    return response;
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
      WebRequest request) {

    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now()
    );

    response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
}
