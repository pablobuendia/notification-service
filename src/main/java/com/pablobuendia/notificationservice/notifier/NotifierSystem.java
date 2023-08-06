package com.pablobuendia.notificationservice.notifier;

import com.pablobuendia.notificationservice.notifier.NotifierConfiguration.NotifierType;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class NotifierSystem {

  private final Map<NotifierType, Notifier> notifiers = new HashMap<>();

  public void send(String type, String userId, String message) {
    notifiers.get(NotifierType.valueOf(type)).send(type, userId, message);
  }

}
