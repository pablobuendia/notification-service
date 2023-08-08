package com.pablobuendia.notificationservice.notifier.exception;

public class TypeNotFoundException extends RuntimeException {

  public TypeNotFoundException() {
    super("Limiter type not found");
  }


}
