package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.dto.CreateRestaurantParam;
import com.nobody.OrderSmoothAPI.dto.RestaurantDTO;
import com.nobody.OrderSmoothAPI.entity.Restaurant;
import com.nobody.OrderSmoothAPI.entity.RestaurantI18n;
import com.nobody.OrderSmoothAPI.entity.RestaurantImage;
import com.nobody.OrderSmoothAPI.entity.RestaurantOpeningHours;
import com.nobody.OrderSmoothAPI.entity.RestaurantPayMethod;
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
  public Long createRestaurant(
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

    return restaurant.getRestaurantId();
  }

  @Transactional
  public void updateRestaurant(Restaurant restaurant) {
    restaurantMapper.updateById(restaurant);
  }

  public Restaurant getRestaurant(Long restaurantId) {
    return restaurantMapper.selectOne(
      new MPJLambdaWrapper<Restaurant>()
        .selectAll(Restaurant.class)
        .eq(Restaurant::getRestaurantId, restaurantId)
        .eq(Restaurant::getIsInvalid, false)
        .eq(Restaurant::getIsDeleted, false)
    );
  }

  public RestaurantDTO getRestaurantFullInfo(Long restaurantId) {
    return restaurantMapper.selectJoinOne(
      RestaurantDTO.class,
      new MPJLambdaWrapper<Restaurant>()
        .selectAll(Restaurant.class)
        .selectCollection(RestaurantI18n.class, RestaurantDTO::getI18n)
        .selectCollection(RestaurantImage.class, RestaurantDTO::getImages)
        .selectCollection(
          RestaurantOpeningHours.class,
          RestaurantDTO::getOpeningHours
        )
        .selectCollection(
          RestaurantPayMethod.class,
          RestaurantDTO::getPayMethods
        )
        .leftJoin(
          RestaurantI18n.class,
          RestaurantI18n::getRestaurantId,
          Restaurant::getRestaurantId
        )
        .leftJoin(
          RestaurantOpeningHours.class,
          RestaurantOpeningHours::getRestaurantId,
          Restaurant::getRestaurantId
        )
        .leftJoin(
          RestaurantPayMethod.class,
          RestaurantPayMethod::getRestaurantId,
          Restaurant::getRestaurantId
        )
        .leftJoin(
          RestaurantImage.class,
          RestaurantImage::getRestaurantId,
          Restaurant::getRestaurantId
        )
        .eq(Restaurant::getIsInvalid, false)
        .eq(Restaurant::getIsDeleted, false)
        .eq(RestaurantI18n::getIsInvalid, false)
        .eq(RestaurantI18n::getIsDeleted, false)
        .eq(RestaurantOpeningHours::getIsInvalid, false)
        .eq(RestaurantOpeningHours::getIsDeleted, false)
        .eq(RestaurantPayMethod::getIsInvalid, false)
        .eq(RestaurantPayMethod::getIsDeleted, false)
        .and(i ->
          i
            .eq(RestaurantImage::getIsDeleted, false)
            .or()
            .isNull(RestaurantImage::getIsDeleted)
        )
        .and(i ->
          i
            .eq(RestaurantImage::getIsInvalid, false)
            .or()
            .isNull(RestaurantImage::getIsInvalid)
        )
        .eq(Restaurant::getRestaurantId, restaurantId)
    );
  }
}
