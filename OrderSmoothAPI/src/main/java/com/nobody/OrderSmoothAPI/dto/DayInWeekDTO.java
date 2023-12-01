package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayInWeekDTO implements Serializable {

  private Integer dayInWeekId;

  private List<DayInWeekI18nDTO> i18n;
}
