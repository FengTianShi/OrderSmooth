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

  @NotBlank
  @Size(max = 5)
  private String langCode;

  @NotBlank
  @Size(max = 100)
  private String restaurantName;

  @NotBlank
  @Size(max = 11)
  @Pattern(regexp = "\\d+")
  private String phoneNumber;

  @NotBlank
  @Size(max = 10)
  @Pattern(regexp = "\\d+")
  private String postalCode;

  @NotBlank
  @Size(max = 500)
  private String address;

  @NotBlank
  @Size(max = 2000)
  private String description;

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
  private Integer defultServiceFee;

  @NotNull
  @Min(0)
  @Max(100)
  private Integer defultTax;

  @NotNull
  private Boolean isDisplayServiceFee;

  @NotNull
  private Boolean isDisplayTax;

  @Valid
  @NotNull
  @Size(min = 1)
  @KeyNotDuplicate(key = "dayInWeekId")
  List<WeekOpeningHoursParam> weekOpeningHours;
}
