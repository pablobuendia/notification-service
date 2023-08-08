package com.pablobuendia.notificationservice.notifier;

import com.pablobuendia.notificationservice.notifier.ratelimiter.RateLimiter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class RateLimiterConfig {

  private static final String PREFIX = "rate-limiter.";
  private final Environment env;

  @Bean
  public Map<String, Notifier> notifiers() {
    val map = new HashMap<String, Notifier>();

    val limiters = env.getProperty(PREFIX + "limiters", List.class);

    if (limiters != null) {
      limiters.forEach(limiter -> {
        val seconds = env.getProperty(PREFIX + limiter + ".seconds", Integer.class);
        val tokens = env.getProperty(PREFIX + limiter + ".tokens", Integer.class);
        val rateLimiter = new RateLimiter(seconds, tokens);

        map.put((String) limiter, new Notifier(rateLimiter));
      });
    }

    return map;
  }

}
