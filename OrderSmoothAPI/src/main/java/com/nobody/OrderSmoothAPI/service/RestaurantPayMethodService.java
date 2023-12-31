package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.entity.RestaurantPayMethod;
import com.nobody.OrderSmoothAPI.mapper.RestaurantPayMethodMapper;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantPayMethodService {

  private final RestaurantPayMethodMapper restaurantPayMethodMapper;

  public RestaurantPayMethodService(
    RestaurantPayMethodMapper restaurantPayMethodMapper
  ) {
    this.restaurantPayMethodMapper = restaurantPayMethodMapper;
  }

  @Transactional
  public void createRestaurantPayMethod(
    Long restaurantId,
    List<Integer> payMethodIdList
  ) {
    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

    for (Integer payMethodId : payMethodIdList) {
      restaurantPayMethodMapper.insert(
        RestaurantPayMethod
          .builder()
          .restaurantId(restaurantId)
          .payMethodId(payMethodId)
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

  public Long getRestaurantPayMethodCount(Long restaurantId) {
    return restaurantPayMethodMapper.selectCount(
      new MPJLambdaWrapper<RestaurantPayMethod>()
        .eq(RestaurantPayMethod::getRestaurantId, restaurantId)
        .eq(RestaurantPayMethod::getIsInvalid, false)
        .eq(RestaurantPayMethod::getIsDeleted, false)
    );
  }

  @Transactional
  public void deleteRestaurantPayMethod(Long restaurantId) {
    restaurantPayMethodMapper.delete(
      new MPJLambdaWrapper<RestaurantPayMethod>()
        .eq(RestaurantPayMethod::getRestaurantId, restaurantId)
        .eq(RestaurantPayMethod::getIsInvalid, false)
        .eq(RestaurantPayMethod::getIsDeleted, false)
    );
  }
}
