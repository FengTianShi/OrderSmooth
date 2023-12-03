package com.nobody.OrderSmoothAPI.dto;

import com.nobody.OrderSmoothAPI.validate.DateTime;
import com.nobody.OrderSmoothAPI.validate.DateTimeInterval;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DateTimeInterval(
  startTime = { "openingTime" },
  endTime = { "closingTime" },
  format = "HH:mm"
)
public class DayInWeekOpeningHoursParam implements Serializable {

  @NotBlank
  @DateTime(format = "HH:mm")
  private String openingTime;

  @NotBlank
  @DateTime(format = "HH:mm")
  private String closingTime;
}
