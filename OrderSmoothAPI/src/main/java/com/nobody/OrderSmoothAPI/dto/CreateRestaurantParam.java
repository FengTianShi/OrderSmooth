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
public class CreateRestaurantParam implements Serializable {

  @NotNull
  @Positive
  private Integer genreId;

  @Valid
  @NotNull
  RestaurantI18nParam restaurantI18n;

  @NotBlank
  @Size(max = 11)
  @Pattern(regexp = "\\d+")
  private String tel;

  @NotBlank
  @Size(max = 10)
  @Pattern(regexp = "\\d+")
  private String postalCode;

  @NotNull
  @Positive
  private Integer currencyId;

  @NotNull
  @Size(min = 1)
  @NotDuplicate
  List<Integer> payMethodIds;

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

  @Valid
  @NotNull
  @Size(min = 1)
  @KeyNotDuplicate(key = "dayInWeekId")
  List<RestaurantOpeningHoursParam> restaurantOpeningHours;
}
