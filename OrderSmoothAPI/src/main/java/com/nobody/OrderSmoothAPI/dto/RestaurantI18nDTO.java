package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantI18nDTO implements Serializable {

  private Long seq;

  private Long restaurantId;

  private String langCode;

  private String restaurantName;

  private String restaurantAddress;

  private String restaurantDescription;

  private Boolean isInvalid;

  private Boolean isDeleted;

  private OffsetDateTime insertTime;

  private String insertedBy;

  private OffsetDateTime updateTime;

  private String updatedBy;
}
