package com.pablobuendia.notificationservice.notifier.ratelimiter;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RateLimiterConfig {

  public static final String RATELIMITER = "rate-limiter.";
  @Autowired
  private Environment env;

  private RateLimiter createLimiter(String type) {
    val rateLimiter = new RateLimiter();
    rateLimiter.setRateLimiter(
        env.getProperty(RATELIMITER + type + ".second", Integer.class),
        env.getProperty(RATELIMITER + type + ".minute", Integer.class),
        env.getProperty(RATELIMITER + type + ".hour", Integer.class),
        env.getProperty(RATELIMITER + type + ".day", Integer.class));

    return rateLimiter;
  }

  @Bean
  public RateLimiter statusLimiter() {
    return createLimiter("status");
  }

  @Bean
  public RateLimiter newsLimiter() {
    return createLimiter("news");
  }

  @Bean
  public RateLimiter marketingLimiter() {
    return createLimiter("marketing");
  }

}
