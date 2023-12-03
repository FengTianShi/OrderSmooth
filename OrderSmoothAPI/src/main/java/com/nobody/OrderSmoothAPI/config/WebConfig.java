package com.nobody.OrderSmoothAPI.config;

import com.nobody.OrderSmoothAPI.interceptor.OwnerSessionInterceptor;
import com.nobody.OrderSmoothAPI.interceptor.UpdateRestaurantInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final OwnerSessionInterceptor ownerSessionInterceptor;

  private final UpdateRestaurantInterceptor updateRestaurantInterceptor;

  public WebConfig(
    OwnerSessionInterceptor ownerSessionInterceptor,
    UpdateRestaurantInterceptor updateRestaurantInterceptor
  ) {
    this.ownerSessionInterceptor = ownerSessionInterceptor;
    this.updateRestaurantInterceptor = updateRestaurantInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
      .addInterceptor(ownerSessionInterceptor)
      .addPathPatterns("/master/**")
      .addPathPatterns("/restaurant/**");

    registry
      .addInterceptor(updateRestaurantInterceptor)
      .addPathPatterns("/restaurant/**")
      .excludePathPatterns("/restaurant");
  }
}
