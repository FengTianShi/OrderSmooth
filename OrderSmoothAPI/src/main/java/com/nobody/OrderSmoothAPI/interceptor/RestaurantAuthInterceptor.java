package com.nobody.OrderSmoothAPI.interceptor;

import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.entity.Restaurant;
import com.nobody.OrderSmoothAPI.service.RestaurantService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RestaurantAuthInterceptor implements HandlerInterceptor {

  private final Logger logger = LoggerFactory.getLogger(
    RestaurantAuthInterceptor.class
  );

  private RestaurantService restaurantService;

  public RestaurantAuthInterceptor(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @Override
  public boolean preHandle(
    HttpServletRequest request,
    HttpServletResponse response,
    Object handler
  ) throws Exception {
    String path = request.getRequestURI();
    String[] pathArray = path.split("/");
    Long restaurantId = Long.parseLong(pathArray[pathArray.length - 1]);

    Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
    if (restaurant == null) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return false;
    }

    Owner owner = RequestUtils.getOwner(request);
    if (owner.getOwnerId() != restaurant.getOwnerId()) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN);
      return false;
    }

    logger.info(
      "Owner {} update restaurant {} auth confirmed",
      owner.getOwnerId(),
      restaurantId
    );
    return true;
  }
}
