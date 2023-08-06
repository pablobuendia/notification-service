package com.pablobuendia.notificationservice;

import com.pablobuendia.notificationservice.notifier.NotifierSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class NotificationServiceApplication {

  private final NotifierSystem notifierSystem;

  public static void main(String[] args) {
    SpringApplication.run(NotificationServiceApplication.class, args);
  }

  @GetMapping("/send")
  public void send() throws InterruptedException {
    notifierSystem.send("NEWS", "user", "news 1");
    notifierSystem.send("NEWS", "user", "news 1");
    notifierSystem.send("NEWS", "user", "news 1");
  }

}
