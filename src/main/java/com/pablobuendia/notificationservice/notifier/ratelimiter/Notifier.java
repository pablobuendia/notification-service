package com.pablobuendia.notificationservice.notifier.ratelimiter;

import com.pablobuendia.notificationservice.notifier.dto.Notification;
import com.pablobuendia.notificationservice.notifier.dto.SecondsRequest;
import com.pablobuendia.notificationservice.notifier.dto.TokensRequest;
import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Notifier {

  private final RateLimiter rateLimiter;
  private final Map<String, String> messagesQueue = new HashMap<>();

  public void send(@NotNull final Notification notification) {
    if (rateLimiter.acquire()) {
      log.info("SENDING MESSAGE to user {} with type {} and message {}", notification.getUserId(),
          notification.getType(), notification.getMessage());
    } else {
      log.warn("NOT SENDING MESSAGE: rate limit exceeded for user {}", notification.getUserId());
      loadQueuedMessages(notification.getUserId(), notification.getMessage());
    }
  }

  private void loadQueuedMessages(final String userId, final String message) {
    messagesQueue.put(userId, message);

    // TODO in version 2: send left messages in an async way

    if (messagesQueue.size() > 100) { // Is memory is too full
      messagesQueue.clear();
    }
  }

  public void setLimiterSeconds(@NotNull SecondsRequest secondsRequest) {
    log.info("Setting seconds to {} for type {}", secondsRequest.getSeconds(),
        secondsRequest.getType());
    if (secondsRequest.getSeconds() == null || secondsRequest.getSeconds() <= 0) {
      return;
    }
    rateLimiter.setLimit(secondsRequest.getSeconds());
  }

  public void setTokens(@NotNull TokensRequest tokensRequest) {
    log.info("Setting tokens to {} for type {}", tokensRequest.getTokens(),
        tokensRequest.getType());
    if (tokensRequest.getTokens() == null || tokensRequest.getTokens() <= 0) {
      return;
    }
    rateLimiter.setTokens(tokensRequest.getTokens());
  }

}
