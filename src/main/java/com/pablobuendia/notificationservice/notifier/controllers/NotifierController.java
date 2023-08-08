package com.pablobuendia.notificationservice.notifier.controllers;

import com.pablobuendia.notificationservice.notifier.dto.Notification;
import com.pablobuendia.notificationservice.notifier.exception.TypeNotFoundException;
import com.pablobuendia.notificationservice.notifier.ratelimiter.Notifier;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotifierController {

  private final Map<String, Notifier> notifiers;

  @PostMapping("/notification")
  public void notification(@Valid @RequestBody final Notification notification) {
    if (!notifiers.containsKey(notification.getType())) {
      throw new TypeNotFoundException();
    }

    notifiers.get(notification.getType()).send(notification);
  }

}
