package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;
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
public class RestaurantI18nParam implements Serializable {

  @NotBlank
  @Size(max = 5)
  private String langCode;

  @NotBlank
  @Size(max = 100)
  private String restaurantName;

  @NotBlank
  @Size(max = 500)
  private String address;

  @NotBlank
  @Size(max = 2000)
  private String description;
}
