package com.pablobuendia.notificationservice.notifier;

import com.pablobuendia.notificationservice.notifier.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Notifier {

  private final RateLimiter rateLimiter;

  public void send(String type, String userId, String message) {
    if (rateLimiter.acquire()) {
      log.info("SENDING MESSAGE to user {} with type {} and message {}", userId, type, message);
    } else {
      log.warn("NOT SENDING MESSAGE: rate limit exceeded for user {}", userId);
    }
  }

}
