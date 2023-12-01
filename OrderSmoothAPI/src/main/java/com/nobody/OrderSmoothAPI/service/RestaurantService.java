package com.nobody.OrderSmoothAPI.service;

import com.nobody.OrderSmoothAPI.dto.CreateRestaurantParam;
import com.nobody.OrderSmoothAPI.entity.Restaurant;
import com.nobody.OrderSmoothAPI.mapper.RestaurantMapper;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantService {

  private final RestaurantMapper restaurantMapper;

  private final RestaurantI18nService restaurantI18nService;

  private final RestaurantPayMethodService restaurantPayMethodService;

  private final RestaurantOpeningHoursService restaurantOpeningHoursService;

  public RestaurantService(
    RestaurantMapper restaurantMapper,
    RestaurantI18nService restaurantI18nService,
    RestaurantPayMethodService restaurantPayMethodService,
    RestaurantOpeningHoursService restaurantOpeningHoursService
  ) {
    this.restaurantMapper = restaurantMapper;
    this.restaurantI18nService = restaurantI18nService;
    this.restaurantPayMethodService = restaurantPayMethodService;
    this.restaurantOpeningHoursService = restaurantOpeningHoursService;
  }

  @Transactional
  public void createRestaurant(
    Long ownerId,
    CreateRestaurantParam createRestaurantParam
  ) {
    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

    Restaurant restaurant = Restaurant
      .builder()
      .ownerId(ownerId)
      .genreId(createRestaurantParam.getGenreId())
      .restaurantLogoAddress(null)
      .restaurantTel(createRestaurantParam.getTel())
      .restaurantPostalCode(createRestaurantParam.getPostalCode())
      .restaurantLongitude(null)
      .restaurantLatitude(null)
      .restaurantServiceDistance(null)
      .currencyId(createRestaurantParam.getCurrencyId())
      .defaultServiceFee(createRestaurantParam.getDefaultServiceFee())
      .defaultTax(createRestaurantParam.getDefaultTax())
      .isDisplayServiceFee(createRestaurantParam.getIsDisplayServiceFee())
      .isDisplayTax(createRestaurantParam.getIsDisplayTax())
      .wifiSsid(null)
      .wifiPassword(null)
      .isInvalid(false)
      .isDeleted(false)
      .insertTime(now)
      .insertedBy("SYSTEM")
      .updateTime(now)
      .updatedBy("SYSTEM")
      .build();

    restaurantMapper.insert(restaurant);

    restaurantI18nService.createRestaurantI18n(
      restaurant.getRestaurantId(),
      createRestaurantParam.getRestaurantI18n()
    );

    restaurantPayMethodService.createRestaurantPayMethod(
      restaurant.getRestaurantId(),
      createRestaurantParam.getPayMethodIds()
    );

    restaurantOpeningHoursService.createRestaurantOpeningHours(
      restaurant.getRestaurantId(),
      createRestaurantParam.getRestaurantOpeningHours()
    );
  }
}
