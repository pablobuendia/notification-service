package com.pablobuendia.notificationservice.notifier.ratelimiter;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RateLimiterTest {

  private RateLimiter rateLimiter;

  @Test
  void acquireOneToken() {
    rateLimiter = new RateLimiter(1, 1);
    boolean token = rateLimiter.acquire();

    assertTrue(token);
    assertEquals(0, rateLimiter.getCounter());
  }

  @Test
  void acquireTwoTokens() {
    rateLimiter = new RateLimiter(1, 2);
    rateLimiter.acquire();
    boolean token = rateLimiter.acquire();

    assertTrue(token);
    assertEquals(0, rateLimiter.getCounter());
  }

  @Test
  void acquireEmptyTokens() {
    rateLimiter = new RateLimiter(1, 0);
    boolean token = rateLimiter.acquire();

    assertTrue(token);
    assertEquals(0, rateLimiter.getCounter());
  }

  @Test
  void acquireNullTokens() {
    rateLimiter = new RateLimiter(1, null);
    boolean token = rateLimiter.acquire();

    assertTrue(token);
    assertEquals(0, rateLimiter.getCounter());
  }

  @Test
  void acquireNullLimit() {
    rateLimiter = new RateLimiter(null, 1);
    boolean token = rateLimiter.acquire();

    assertTrue(token);
    assertEquals(0, rateLimiter.getCounter());
  }

  @Test
  void acquireLimit() {
    rateLimiter = new RateLimiter(1, 1);
    rateLimiter.acquire();
    boolean token = rateLimiter.acquire();

    assertFalse(token);
  }

  @Test
  void acquireLimitWithReset() throws InterruptedException {
    rateLimiter = new RateLimiter(1, 1);
    rateLimiter.acquire();
    await().atLeast(1, TimeUnit.SECONDS).until(() -> rateLimiter.getCounter() == 1);
    boolean token = rateLimiter.acquire();

    assertTrue(token);
  }

  @Test
  void acquireLimitWithResetAndTokens() throws InterruptedException {
    rateLimiter = new RateLimiter(1, 2);
    rateLimiter.acquire();
    rateLimiter.acquire();
    await().atLeast(1, TimeUnit.SECONDS).until(() -> rateLimiter.getCounter() == 2);
    rateLimiter.acquire();
    boolean token = rateLimiter.acquire();

    assertTrue(token);
  }
}