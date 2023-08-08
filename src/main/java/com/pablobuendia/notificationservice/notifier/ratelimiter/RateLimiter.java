package com.pablobuendia.notificationservice.notifier.ratelimiter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Getter
@Slf4j
public class RateLimiter {

  private static final Integer DEFAULT_SECONDS_LIMIT = 60;
  private static final Integer DEFAULT_TOKENS = 1;

  @Setter
  private Integer limit;

  @Setter
  private Integer tokens;

  private Integer counter = 0;

  private Long nextReset = 0L;

  public RateLimiter(final Integer limit, final Integer tokens) {
    log.info("Creating rate limiter with limit {} and tokens {}", limit, tokens);
    if (limit != null && limit > 0) {
      this.limit = limit;
    } else {
      this.limit = DEFAULT_SECONDS_LIMIT;
    }

    if (tokens != null && tokens > 0) {
      this.tokens = tokens;
    } else {
      this.tokens = DEFAULT_TOKENS;
    }
  }

  public synchronized boolean acquire() {
    checkReset();

    if (counter == 0) {
      return false;
    } else {
      counter--;
      return true;
    }
  }

  private void checkReset() {
    val currentTime = System.currentTimeMillis();
    if (currentTime > nextReset) {
      counter = tokens;
      nextReset = currentTime + limit * 1000L;
    }
  }

}
