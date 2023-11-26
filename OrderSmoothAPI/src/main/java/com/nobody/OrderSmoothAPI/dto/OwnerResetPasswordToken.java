package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerResetPasswordToken implements Serializable {

  private String ownerEmail;

  private String newPassword;

  private String otp;
}
