package com.nobody.OrderSmoothAPI.config;

import com.nobody.OrderSmoothAPI.interceptor.OwnerSessionInterceptor;
import com.nobody.OrderSmoothAPI.interceptor.RestaurantAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final OwnerSessionInterceptor ownerSessionInterceptor;

  private final RestaurantAuthInterceptor restaurantAuthInterceptor;

  public WebConfig(
    OwnerSessionInterceptor ownerSessionInterceptor,
    RestaurantAuthInterceptor restaurantAuthInterceptor
  ) {
    this.ownerSessionInterceptor = ownerSessionInterceptor;
    this.restaurantAuthInterceptor = restaurantAuthInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
      .addInterceptor(ownerSessionInterceptor)
      .addPathPatterns("/master/**")
      .addPathPatterns("/restaurant/**");

    registry
      .addInterceptor(restaurantAuthInterceptor)
      .addPathPatterns("/restaurant/**")
      .excludePathPatterns("/restaurant");
  }
}
