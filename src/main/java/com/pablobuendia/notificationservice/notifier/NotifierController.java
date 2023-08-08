package com.pablobuendia.notificationservice.notifier;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotifierController {

  private final NotifierSystem notifierSystem;

  @PostMapping("/send")
  public void send(@Valid @RequestBody Notification notification) {
    notifierSystem.send(notification.getType(), notification.getUserId(),
        notification.getMessage());
  }
}
