package com.nobody.OrderSmoothAPI.service;

import com.nobody.OrderSmoothAPI.dto.DayInWeekOpeningHoursParam;
import com.nobody.OrderSmoothAPI.dto.RestaurantOpeningHoursParam;
import com.nobody.OrderSmoothAPI.entity.RestaurantOpeningHours;
import com.nobody.OrderSmoothAPI.mapper.RestaurantOpeningHoursMapper;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantOpeningHoursService {

  private final RestaurantOpeningHoursMapper restaurantOpeningHoursMapper;

  public RestaurantOpeningHoursService(
    RestaurantOpeningHoursMapper restaurantOpeningHoursMapper
  ) {
    this.restaurantOpeningHoursMapper = restaurantOpeningHoursMapper;
  }

  @Transactional
  public void createRestaurantOpeningHours(
    Long restaurantId,
    List<RestaurantOpeningHoursParam> restaurantOpeningHoursParams
  ) {
    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

    for (RestaurantOpeningHoursParam restaurantOpeningHoursParam : restaurantOpeningHoursParams) {
      for (DayInWeekOpeningHoursParam dayInWeekOpeningHoursParam : restaurantOpeningHoursParam.getDayInWeekOpeningHours()) {
        restaurantOpeningHoursMapper.insert(
          RestaurantOpeningHours
            .builder()
            .restaurantId(restaurantId)
            .dayInWeekId(restaurantOpeningHoursParam.getDayInWeekId())
            .openTime(
              LocalTime.parse(dayInWeekOpeningHoursParam.getStartTime())
            )
            .closeTime(LocalTime.parse(dayInWeekOpeningHoursParam.getEndTime()))
            .isInvalid(false)
            .isDeleted(false)
            .insertTime(now)
            .insertedBy("SYSTEM")
            .updateTime(now)
            .updatedBy("SYSTEM")
            .build()
        );
      }
    }
  }
}
