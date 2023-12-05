package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.entity.RestaurantImage;
import com.nobody.OrderSmoothAPI.mapper.RestaurantImageMapper;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantImageService {

  private final RestaurantImageMapper restaurantImageMapper;

  public RestaurantImageService(RestaurantImageMapper restaurantImageMapper) {
    this.restaurantImageMapper = restaurantImageMapper;
  }

  public List<RestaurantImage> getAllRestaurantImage(Long restaurantId) {
    return restaurantImageMapper.selectList(
      new MPJLambdaWrapper<RestaurantImage>()
        .eq(RestaurantImage::getRestaurantId, restaurantId)
        .eq(RestaurantImage::getIsInvalid, false)
        .eq(RestaurantImage::getIsDeleted, false)
    );
  }

  public RestaurantImage getRestaurantImage(Long imageId) {
    return restaurantImageMapper.selectOne(
      new MPJLambdaWrapper<RestaurantImage>()
        .eq(RestaurantImage::getSeq, imageId)
        .eq(RestaurantImage::getIsInvalid, false)
        .eq(RestaurantImage::getIsDeleted, false)
    );
  }

  @Transactional
  public void createRestaurantImage(
    Long restaurantId,
    RestaurantImage restaurantImage
  ) {
    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

    restaurantImageMapper.insert(
      RestaurantImage
        .builder()
        .restaurantId(restaurantId)
        .imageAddress(restaurantImage.getImageAddress())
        .isInvalid(false)
        .isDeleted(false)
        .insertTime(now)
        .insertedBy("SYSTEM")
        .updateTime(now)
        .updatedBy("SYSTEM")
        .build()
    );
  }

  public Long getRestaurantImageCount(Long restaurantId) {
    return restaurantImageMapper.selectCount(
      new MPJLambdaWrapper<RestaurantImage>()
        .eq(RestaurantImage::getRestaurantId, restaurantId)
        .eq(RestaurantImage::getIsInvalid, false)
        .eq(RestaurantImage::getIsDeleted, false)
    );
  }

  @Transactional
  public void deleteRestaurantImage(Long imageId) {
    restaurantImageMapper.delete(
      new MPJLambdaWrapper<RestaurantImage>()
        .eq(RestaurantImage::getSeq, imageId)
        .eq(RestaurantImage::getIsInvalid, false)
        .eq(RestaurantImage::getIsDeleted, false)
    );
  }
}
