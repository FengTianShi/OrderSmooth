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
  startTime = { "startTime" },
  endTime = { "endTime" },
  format = "HH:mm"
)
public class DayInWeekOpeningHoursParam implements Serializable {

  @NotBlank
  @DateTime(format = "HH:mm")
  private String startTime;

  @NotBlank
  @DateTime(format = "HH:mm")
  private String endTime;
}
