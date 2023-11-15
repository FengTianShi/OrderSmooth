package com.nobody.OrderSmoothAPI.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HttpConverterConfig implements WebMvcConfigurer {

  @Value("${ordersmooth.image.path}")
  private String imagePath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
      .addResourceHandler("/image/**")
      .addResourceLocations("file:" + imagePath);
  }
}
