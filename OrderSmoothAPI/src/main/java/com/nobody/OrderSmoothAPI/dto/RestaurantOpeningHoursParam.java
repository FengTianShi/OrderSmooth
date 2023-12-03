package com.nobody.OrderSmoothAPI.dto;

import com.nobody.OrderSmoothAPI.validate.NotOverlap;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
public class RestaurantOpeningHoursParam implements Serializable {

  @NotNull
  @Positive
  private Integer dayInWeekId;

  @Valid
  @NotNull
  @Size(min = 1)
  @NotOverlap(format = "HH:mm", startTime = "openingTime", endTime = "closingTime")
  List<DayInWeekOpeningHoursParam> dayInWeekOpeningHours;
}
