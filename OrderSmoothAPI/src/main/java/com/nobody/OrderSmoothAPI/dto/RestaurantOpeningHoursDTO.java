package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantOpeningHoursDTO implements Serializable {

  private Long seq;

  private Long restaurantId;

  private Integer dayInWeekId;

  private LocalTime openTime;

  private LocalTime closeTime;

  private Boolean isInvalid;

  private Boolean isDeleted;

  private OffsetDateTime insertTime;

  private String insertedBy;

  private OffsetDateTime updateTime;

  private String updatedBy;
}
