package com.nobody.OrderSmoothAPI.interceptor;

import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.service.OwnerService;
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

  private OwnerService ownerService;

  public RestaurantAuthInterceptor(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @Override
  public boolean preHandle(
    HttpServletRequest request,
    HttpServletResponse response,
    Object handler
  ) throws Exception {
    // Owner owner = RequestUtils.getOwner(request);
    // if (owner == null) {
    //   return false;
    // }

    // if (ownerService.getOwnerById(owner.getOwnerId()) == null) {
    //   return false;
    // }

    // logger.info("Owner {} is authorized", owner.getOwnerId());

    //TODO prevent multiple requests from the same IP address in a short period of time

    return true;
  }
}
