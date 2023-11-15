package com.nobody.OrderSmoothAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class OwnerSigninParamDTO implements Serializable {

  @NotBlank
  @Email
  @Size(max = 100)
  private String ownerEmail;

  @NotBlank
  @Size(max = 100)
  private String ownerPassword;

  @JsonIgnore
  private String ipAddress;

  @JsonIgnore
  private String deviceInfo;
}
