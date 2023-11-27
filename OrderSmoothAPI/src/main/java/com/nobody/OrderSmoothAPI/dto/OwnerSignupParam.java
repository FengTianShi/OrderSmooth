package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OwnerSignupParam implements Serializable {

  @NotBlank
  @Size(max = 100)
  private String ownerName;

  @NotBlank
  @Email
  @Size(max = 100)
  private String ownerEmail;

  @NotBlank
  @Size(min = 8, max = 100)
  private String ownerPassword;
}
