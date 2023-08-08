package com.pablobuendia.notificationservice.sender;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class ScheduledSender {

  private final WebClient webClient = WebClient.create("http://localhost:8080");
  private final Random random = new Random();

  @Scheduled(fixedRate = 30000)
  @Async
  public void sendMessage() {
    sendMessage(buildMessage());
  }

  private void sendMessage(Notification message) {
    webClient.post()
        .uri("/notification")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(message))
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  private Notification buildMessage() {
    val randomValue = String.valueOf(random.nextInt(10));
    return Notification.builder().type("status").userId(randomValue)
        .message("Hello world " + randomValue).build();
  }

}
