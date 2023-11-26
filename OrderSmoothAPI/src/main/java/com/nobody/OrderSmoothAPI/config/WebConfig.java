package com.nobody.OrderSmoothAPI.config;

import com.nobody.OrderSmoothAPI.interceptor.RestaurantAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final RestaurantAuthInterceptor restaurantAuthInterceptor;

  public WebConfig(RestaurantAuthInterceptor restaurantAuthInterceptor) {
    this.restaurantAuthInterceptor = restaurantAuthInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
      .addInterceptor(restaurantAuthInterceptor)
      .addPathPatterns("/restaurant/**");
  }
}
