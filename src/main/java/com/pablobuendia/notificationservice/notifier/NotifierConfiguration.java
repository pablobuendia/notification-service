package com.pablobuendia.notificationservice.notifier;


import static com.pablobuendia.notificationservice.notifier.NotifierConfiguration.NotifierType.MARKETING;
import static com.pablobuendia.notificationservice.notifier.NotifierConfiguration.NotifierType.NEWS;
import static com.pablobuendia.notificationservice.notifier.NotifierConfiguration.NotifierType.STATUS;

import com.pablobuendia.notificationservice.notifier.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NotifierConfiguration {

  private final RateLimiter statusLimiter;
  private final RateLimiter newsLimiter;
  private final RateLimiter marketingLimiter;

  @Bean
  public NotifierSystem notifierSystem() {
    val notifierSystem = new NotifierSystem();
    notifierSystem.getNotifiers().put(STATUS, new Notifier(statusLimiter));
    notifierSystem.getNotifiers().put(NEWS, new Notifier(newsLimiter));
    notifierSystem.getNotifiers().put(MARKETING, new Notifier(marketingLimiter));

    return notifierSystem;
  }

  public enum NotifierType {

    STATUS,
    NEWS,
    MARKETING,

  }

}
