package com.pablobuendia.notificationservice.notifier.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
public class SecondsRequest {

  @NotBlank(message = "Notification type is mandatory")
  private String type;

  @PositiveOrZero
  private Integer seconds;
}
