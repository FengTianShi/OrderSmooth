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
public class OwnerSignupToken implements Serializable {

  private String ownerName;

  private String ownerEmail;

  private String ownerPassword;

  private String otp;
}
