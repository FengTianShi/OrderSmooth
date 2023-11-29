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
public class WeekDTO implements Serializable {

  private Integer weekId;

  private List<WeekI18nDTO> i18n;
}
