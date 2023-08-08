package com.pablobuendia.notificationservice.notifier.ratelimiter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class RateLimiter {

  private static final Integer DEFAULT_SECONDS_LIMIT = 60;

  private Integer limit = 0;
  private Integer tokens = 1;
  private Long nextReset = 0L;

  public void setRateLimiter(final Integer limit) {
    if (limit != null && limit > 0) {
      this.limit = limit;
    } else {
      this.limit = DEFAULT_SECONDS_LIMIT;
    }
  }

  public synchronized boolean acquire() {
    checkReset();

    if (tokens == 0) {
      return false;
    } else {
      tokens--;
      return true;
    }
  }

  private void checkReset() {
    val currentTime = System.currentTimeMillis();
    if (currentTime > nextReset) {
      tokens = 1;
      nextReset = currentTime + limit * 1000L;
    }
  }

}
