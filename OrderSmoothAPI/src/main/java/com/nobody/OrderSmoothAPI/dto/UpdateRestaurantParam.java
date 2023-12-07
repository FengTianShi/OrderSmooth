package com.nobody.OrderSmoothAPI.dto;

import com.nobody.OrderSmoothAPI.validate.KeyNotDuplicate;
import com.nobody.OrderSmoothAPI.validate.NotDuplicate;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRestaurantParam implements Serializable {

  @NotNull
  @Positive
  private Integer genreId;

  @NotBlank
  @Size(max = 11)
  @Pattern(regexp = "\\d+")
  private String restaurantTel;

  @NotBlank
  @Size(max = 10)
  @Pattern(regexp = "\\d+")
  private String restaurantPostalCode;

  @NotNull
  private Double restaurantLatitude;

  @NotNull
  private Double restaurantLongitude;

  @Min(1)
  @Max(99999)
  private Integer restaurantServiceDistance;

  @NotNull
  private Boolean isLimitServiceDistance;

  @NotNull
  @Positive
  private Integer currencyId;

  @NotNull
  @Min(0)
  @Max(100)
  private Integer defaultServiceFee;

  @NotNull
  @Min(0)
  @Max(100)
  private Integer defaultTax;

  @NotNull
  private Boolean isDisplayServiceFee;

  @NotNull
  private Boolean isDisplayTax;

  private String wifiSsid;

  private String wifiPassword;

  @NotNull
  @Size(min = 1)
  @NotDuplicate
  List<Integer> payMethodIds;

  @Valid
  @NotNull
  @Size(min = 1)
  @KeyNotDuplicate(key = "langCode")
  List<RestaurantI18nParam> restaurantI18ns;

  @Valid
  @NotNull
  @Size(min = 1)
  @KeyNotDuplicate(key = "dayInWeekId")
  List<RestaurantOpeningHoursParam> restaurantOpeningHours;
}
