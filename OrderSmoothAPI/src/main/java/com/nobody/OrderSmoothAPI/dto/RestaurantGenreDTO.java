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
public class RestaurantGenreDTO implements Serializable {

  private Integer genreId;

  private List<RestaurantGenreI18nDTO> i18n;
}
