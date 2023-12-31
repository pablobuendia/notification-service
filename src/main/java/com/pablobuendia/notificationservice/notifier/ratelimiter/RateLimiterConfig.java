package com.pablobuendia.notificationservice.notifier.ratelimiter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RateLimiterConfig {

  private static final String PREFIX = "rate-limiter.";
  private final Environment env;

  @Bean
  public Map<String, Notifier> notifiers() {
    val map = new HashMap<String, Notifier>();

    val limiters = getLimiterNames();

    loadLimiters(limiters, map);

    return map;
  }

  private void loadLimiters(List limiters, HashMap<String, Notifier> map) {
    if (limiters != null) {
      limiters.forEach(limiter -> {
        val seconds = env.getProperty(PREFIX + limiter + ".seconds", Integer.class);
        val tokens = env.getProperty(PREFIX + limiter + ".tokens", Integer.class);
        val rateLimiter = new RateLimiter(seconds, tokens);

        map.put((String) limiter, new Notifier(rateLimiter));
      });
    }
    log.info("Loading limiters: {}", limiters);
  }

  private List getLimiterNames() {
    return env.getProperty(PREFIX + "limiters", List.class);
  }

}
