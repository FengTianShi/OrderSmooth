package com.nobody.OrderSmoothAPI.service;

import com.nobody.OrderSmoothAPI.dto.RestaurantI18nParam;
import com.nobody.OrderSmoothAPI.entity.RestaurantI18n;
import com.nobody.OrderSmoothAPI.mapper.RestaurantI18nMapper;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantI18nService {

  private final RestaurantI18nMapper restaurantI18nMapper;

  public RestaurantI18nService(RestaurantI18nMapper restaurantI18nMapper) {
    this.restaurantI18nMapper = restaurantI18nMapper;
  }

  @Transactional
  public void createRestaurantI18n(
    Long restaurantId,
    RestaurantI18nParam restaurantI18nParam
  ) {
    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

    restaurantI18nMapper.insert(
      RestaurantI18n
        .builder()
        .restaurantId(restaurantId)
        .langCode(restaurantI18nParam.getLangCode())
        .restaurantName(restaurantI18nParam.getRestaurantName())
        .restaurantAddress(restaurantI18nParam.getAddress())
        .restaurantDescription(restaurantI18nParam.getDescription())
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
