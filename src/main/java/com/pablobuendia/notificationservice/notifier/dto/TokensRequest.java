package com.pablobuendia.notificationservice.notifier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
public class TokensRequest {

  @NotBlank(message = "Notification type is mandatory")
  private String type;

  @Positive
  private Integer tokens;
}
