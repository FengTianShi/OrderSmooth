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
public class PayMethodDTO implements Serializable {

  private Integer payMethodId;

  private List<PayMethodI18nDTO> i18n;
}
