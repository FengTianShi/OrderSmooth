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
public class OwnerSessionInterceptor implements HandlerInterceptor {

  private final Logger logger = LoggerFactory.getLogger(
    OwnerSessionInterceptor.class
  );

  private OwnerService ownerService;

  public OwnerSessionInterceptor(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @Override
  public boolean preHandle(
    HttpServletRequest request,
    HttpServletResponse response,
    Object handler
  ) throws Exception {
    Owner owner = RequestUtils.getOwner(request);
    if (owner == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return false;
    }

    if (ownerService.getOwnerById(owner.getOwnerId()) == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return false;
    }

    logger.info("Owner {} session confirmed", owner.getOwnerId());
    return true;
  }
}
