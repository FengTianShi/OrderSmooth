package com.nobody.OrderSmoothAPI.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class OwnerAccessInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(
    HttpServletRequest request,
    HttpServletResponse response,
    Object handler
  ) throws Exception {
    System.out.println("Owner Access Interceptor!");
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}
