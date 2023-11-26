package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.entity.RestaurantGenre;
import com.nobody.OrderSmoothAPI.mapper.RestaurantGenreMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RestaurantGenreService {

  private final RestaurantGenreMapper restaurantGenreMapper;

  public RestaurantGenreService(RestaurantGenreMapper restaurantGenreMapper) {
    this.restaurantGenreMapper = restaurantGenreMapper;
  }

  public List<RestaurantGenre> getRestaurantGenre(String langCode) {
    return restaurantGenreMapper.selectList(
      new MPJLambdaWrapper<RestaurantGenre>()
        .select(RestaurantGenre::getGenreId, RestaurantGenre::getGenreName)
        .like(RestaurantGenre::getLangCode, langCode)
        .eq(RestaurantGenre::getIsInvalid, false)
        .eq(RestaurantGenre::getIsDeleted, false)
    );
  }
}
