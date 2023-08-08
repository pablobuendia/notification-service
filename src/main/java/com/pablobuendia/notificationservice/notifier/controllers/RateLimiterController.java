package com.pablobuendia.notificationservice.notifier.controllers;

import com.pablobuendia.notificationservice.notifier.dto.SecondsRequest;
import com.pablobuendia.notificationservice.notifier.dto.TokensRequest;
import com.pablobuendia.notificationservice.notifier.exception.TypeNotFoundException;
import com.pablobuendia.notificationservice.notifier.ratelimiter.Notifier;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RateLimiterController {

  private final Map<String, Notifier> notifiers;

  @PutMapping("/ratelimiter/seconds")
  public void changeSecondsLimit(@Valid @RequestBody final SecondsRequest secondsRequest) {
    if (!notifiers.containsKey(secondsRequest.getType())) {
      throw new TypeNotFoundException();
    }

    notifiers.get(secondsRequest.getType()).setLimiterSeconds(secondsRequest);
  }

  @PutMapping("/ratelimiter/tokens")
  public void notificationTokensLimit(@Valid @RequestBody final TokensRequest tokensRequest) {
    if (!notifiers.containsKey(tokensRequest.getType())) {
      throw new TypeNotFoundException();
    }

    notifiers.get(tokensRequest.getType()).setTokens(tokensRequest);
  }

}
