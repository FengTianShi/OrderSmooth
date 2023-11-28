package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.dto.RestaurantGenreDTO;
import com.nobody.OrderSmoothAPI.entity.RestaurantGenre;
import com.nobody.OrderSmoothAPI.entity.RestaurantGenreI18n;
import com.nobody.OrderSmoothAPI.mapper.RestaurantGenreMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RestaurantGenreService {

  private final RestaurantGenreMapper restaurantGenreMapper;

  public RestaurantGenreService(RestaurantGenreMapper restaurantGenreMapper) {
    this.restaurantGenreMapper = restaurantGenreMapper;
  }

  public List<RestaurantGenreDTO> getRestaurantGenre(String langCode) {
    return restaurantGenreMapper.selectJoinList(
      RestaurantGenreDTO.class,
      new MPJLambdaWrapper<RestaurantGenre>()
        .select(RestaurantGenre::getGenreId)
        .selectCollection(
          RestaurantGenreI18n.class,
          RestaurantGenreDTO::getRestaurantGenreI18nDTOList
        )
        .leftJoin(
          RestaurantGenreI18n.class,
          RestaurantGenreI18n::getGenreId,
          RestaurantGenre::getGenreId
        )
        .like(RestaurantGenreI18n::getLangCode, langCode)
        .eq(RestaurantGenre::getIsInvalid, false)
        .eq(RestaurantGenre::getIsDeleted, false)
        .eq(RestaurantGenreI18n::getIsInvalid, false)
        .eq(RestaurantGenreI18n::getIsDeleted, false)
    );
  }
}
