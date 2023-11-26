package com.nobody.OrderSmoothAPI.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MasterAuthInterceptor implements HandlerInterceptor {

  private final Logger logger = LoggerFactory.getLogger(
    MasterAuthInterceptor.class
  );

  @Override
  public boolean preHandle(
    HttpServletRequest request,
    HttpServletResponse response,
    Object handler
  ) throws Exception {
    logger.info("Master is never authorized for now");
    return false;
  }
}
