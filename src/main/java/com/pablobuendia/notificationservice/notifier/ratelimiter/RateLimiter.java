package com.pablobuendia.notificationservice.notifier.ratelimiter;

import java.util.HashMap;
import java.util.Map;
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

  private static final Integer DEFAULT_MINUTE_LIMIT = 1;
  private Map<LimitType, Long> limiters = new HashMap<>();
  private Integer secondLimit = 0;
  private Integer minuteLimit = 0;
  private Integer hourLimit = 0;
  private Integer dayLimit = 0;
  private Integer secondTokens = -1;
  private Integer minuteTokens = -1;
  private Integer hourTokens = -1;
  private Integer dayTokens = -1;

  public void setRateLimiter(final Integer secondLimit, final Integer minuteLimit,
      final Integer hourLimit, final Integer dayLimit) {
    this.secondLimit = secondLimit;
    this.minuteLimit = minuteLimit;
    this.hourLimit = hourLimit;
    this.dayLimit = dayLimit;

    if (secondLimit != -1) {
      limiters.put(LimitType.SECOND, 0L);
    }
    if (minuteLimit != -1) {
      limiters.put(LimitType.MINUTE, 0L);
    }
    if (hourLimit != -1) {
      limiters.put(LimitType.HOUR, 0L);
    }
    if (dayLimit != -1) {
      limiters.put(LimitType.DAY, 0L);
    }
  }

  public boolean acquire() {
    checkTokensResetTime();

    if (hasEmptyTokens()) {
      return false;
    } else {
      subtractTokensCount();
      return true;
    }
  }

  private void subtractTokensCount() {
    limiters.forEach((limitType, nextReset) -> {
      switch (limitType) {
        case SECOND -> secondTokens--;
        case MINUTE -> minuteTokens--;
        case HOUR -> hourTokens--;
        case DAY -> dayTokens--;
      }
    });
  }

  private boolean hasEmptyTokens() {
    return secondTokens == 0 || minuteTokens == 0 || hourTokens == 0 || dayTokens == 0;
  }

  private void checkTokensResetTime() {
    val currentTime = System.currentTimeMillis();
    limiters.forEach((limitType, nextReset) -> {
      if (currentTime > nextReset) {
        switch (limitType) {
          case SECOND -> secondTokens = secondLimit;
          case MINUTE -> minuteTokens = minuteLimit;
          case HOUR -> hourTokens = hourLimit;
          case DAY -> dayTokens = dayLimit;
        }
        limiters.put(limitType, currentTime + limitType.getMillis());
      }
    });
  }

  @Getter
  public enum LimitType {

    SECOND(1000L),
    MINUTE(60000L),
    HOUR(3600000L),
    DAY(86400000L);

    private final Long millis;

    LimitType(Long millis) {
      this.millis = millis;
    }

  }

}
