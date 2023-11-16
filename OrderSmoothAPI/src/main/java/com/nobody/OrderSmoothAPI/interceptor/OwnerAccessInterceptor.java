package com.nobody.OrderSmoothAPI.interceptor;

import com.nobody.OrderSmoothAPI.common.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import java.security.acl.Owner;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class OwnerAccessInterceptor implements HandlerInterceptor {

  Logger logger = LoggerFactory.getLogger(OwnerAccessInterceptor.class);

  @Override
  public boolean preHandle(
    HttpServletRequest request,
    HttpServletResponse response,
    Object handler
  ) throws Exception {
    String ownerToken = request.getHeader("Authorization");

    if (ownerToken == null || !ownerToken.startsWith("Bearer ")) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return false;
    }

    ownerToken = ownerToken.substring(7);
    try {
      Owner owner = JwtUtils.getContent(ownerToken, Owner.class);

      logger.info(
        owner.toString() + " is accessing " + request.getRequestURI()
      );

      return true;
    } catch (ExpiredJwtException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      logger.info("Token expired");
      return false;
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return false;
    }
  }
}
