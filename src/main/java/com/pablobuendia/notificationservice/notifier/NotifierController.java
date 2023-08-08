package com.pablobuendia.notificationservice.notifier;

import com.pablobuendia.notificationservice.notifier.dto.Notification;
import com.pablobuendia.notificationservice.notifier.dto.SecondsRequest;
import com.pablobuendia.notificationservice.notifier.dto.TokensRequest;
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
    notifiers.get(notification.getType()).send(notification.getType(), notification.getUserId(),
        notification.getMessage());
  }

  @PostMapping("/notification/seconds")
  public void changeSecondsLimit(@Valid @RequestBody final SecondsRequest secondsRequest) {
    notifiers.get(secondsRequest.getType()).setLimiterSeconds(secondsRequest.getSeconds());
  }

  @PostMapping("/notification/tokens")
  public void notificationTokensLimit(@Valid @RequestBody final TokensRequest tokensRequest) {
    notifiers.get(tokensRequest.getType()).setTokens(tokensRequest.getTokens());
  }
}
