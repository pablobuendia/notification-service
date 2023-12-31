package com.pablobuendia.notificationservice.sender;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
@Builder
public class Notification {

  @NotBlank(message = "Notification type is mandatory")
  private String type;

  @NotBlank(message = "User name is mandatory")
  private String userId;

  @NotBlank(message = "Message is mandatory")
  private String message;
}
