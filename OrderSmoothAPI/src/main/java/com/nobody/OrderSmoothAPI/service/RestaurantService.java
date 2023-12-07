package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.dto.CreateRestaurantParam;
import com.nobody.OrderSmoothAPI.dto.RestaurantDTO;
import com.nobody.OrderSmoothAPI.dto.RestaurantI18nParam;
import com.nobody.OrderSmoothAPI.dto.UpdateRestaurantParam;
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
      .restaurantTel(createRestaurantParam.getRestaurantTel())
      .restaurantPostalCode(createRestaurantParam.getRestaurantPostalCode())
      .restaurantLatitude(createRestaurantParam.getRestaurantLatitude())
      .restaurantLongitude(createRestaurantParam.getRestaurantLongitude())
      .currencyId(createRestaurantParam.getCurrencyId())
      .defaultServiceFee(createRestaurantParam.getDefaultServiceFee())
      .defaultTax(createRestaurantParam.getDefaultTax())
      .isDisplayServiceFee(createRestaurantParam.getIsDisplayServiceFee())
      .isDisplayTax(createRestaurantParam.getIsDisplayTax())
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

  public Restaurant getRestaurant(Long restaurantId) {
    return restaurantMapper.selectOne(
      new MPJLambdaWrapper<Restaurant>()
        .selectAll(Restaurant.class)
        .eq(Restaurant::getRestaurantId, restaurantId)
        .eq(Restaurant::getIsInvalid, false)
        .eq(Restaurant::getIsDeleted, false)
    );
  }

  public RestaurantDTO getRestaurantFull(Long restaurantId) {
    return restaurantMapper.selectJoinOne(
      RestaurantDTO.class,
      new MPJLambdaWrapper<Restaurant>()
        .selectAll(Restaurant.class)
        .selectCollection(RestaurantI18n.class, RestaurantDTO::getI18ns)
        .selectCollection(RestaurantImage.class, RestaurantDTO::getImages)
        .selectCollection(
          RestaurantPayMethod.class,
          RestaurantDTO::getPayMethods
        )
        .selectCollection(
          RestaurantOpeningHours.class,
          RestaurantDTO::getOpeningHours
        )
        .leftJoin(
          RestaurantI18n.class,
          RestaurantI18n::getRestaurantId,
          Restaurant::getRestaurantId
        )
        .leftJoin(
          RestaurantImage.class,
          RestaurantImage::getRestaurantId,
          Restaurant::getRestaurantId
        )
        .leftJoin(
          RestaurantPayMethod.class,
          RestaurantPayMethod::getRestaurantId,
          Restaurant::getRestaurantId
        )
        .leftJoin(
          RestaurantOpeningHours.class,
          RestaurantOpeningHours::getRestaurantId,
          Restaurant::getRestaurantId
        )
        .eq(Restaurant::getIsInvalid, false)
        .eq(Restaurant::getIsDeleted, false)
        .eq(RestaurantI18n::getIsInvalid, false)
        .eq(RestaurantI18n::getIsDeleted, false)
        .eq(RestaurantPayMethod::getIsInvalid, false)
        .eq(RestaurantPayMethod::getIsDeleted, false)
        .eq(RestaurantOpeningHours::getIsInvalid, false)
        .eq(RestaurantOpeningHours::getIsDeleted, false)
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

  @Transactional
  public void updateRestaurant(
    Long restaurantId,
    UpdateRestaurantParam updateRestaurantParam
  ) {
    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

    Restaurant restaurant = Restaurant
      .builder()
      .restaurantId(restaurantId)
      .genreId(updateRestaurantParam.getGenreId())
      .restaurantTel(updateRestaurantParam.getRestaurantTel())
      .restaurantPostalCode(updateRestaurantParam.getRestaurantPostalCode())
      .restaurantLatitude(updateRestaurantParam.getRestaurantLatitude())
      .restaurantLongitude(updateRestaurantParam.getRestaurantLongitude())
      .restaurantServiceDistance(
        updateRestaurantParam.getRestaurantServiceDistance()
      )
      .isLimitServiceDistance(updateRestaurantParam.getIsLimitServiceDistance())
      .currencyId(updateRestaurantParam.getCurrencyId())
      .defaultServiceFee(updateRestaurantParam.getDefaultServiceFee())
      .defaultTax(updateRestaurantParam.getDefaultTax())
      .isDisplayServiceFee(updateRestaurantParam.getIsDisplayServiceFee())
      .isDisplayTax(updateRestaurantParam.getIsDisplayTax())
      .wifiSsid(updateRestaurantParam.getWifiSsid())
      .wifiPassword(updateRestaurantParam.getWifiPassword())
      .isInvalid(false)
      .isDeleted(false)
      .insertTime(now)
      .insertedBy("SYSTEM")
      .updateTime(now)
      .updatedBy("SYSTEM")
      .build();

    restaurantMapper.updateById(restaurant);

    restaurantI18nService.deleteRestaurantI18n(restaurantId);
    for (RestaurantI18nParam restaurantI18n : updateRestaurantParam.getRestaurantI18ns()) {
      restaurantI18nService.createRestaurantI18n(restaurantId, restaurantI18n);
    }

    restaurantPayMethodService.deleteRestaurantPayMethod(restaurantId);
    restaurantPayMethodService.createRestaurantPayMethod(
      restaurantId,
      updateRestaurantParam.getPayMethodIds()
    );

    restaurantOpeningHoursService.deleteRestaurantOpeningHours(restaurantId);
    restaurantOpeningHoursService.createRestaurantOpeningHours(
      restaurantId,
      updateRestaurantParam.getRestaurantOpeningHours()
    );
  }

  @Transactional
  public void updateRestaurant(Restaurant restaurant) {
    restaurantMapper.updateById(restaurant);
  }
}
