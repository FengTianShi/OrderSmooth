package com.nobody.OrderSmoothAPI.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
      .addMapping("/**") // Allow cross-origin access for all endpoints in the project.
      .allowedOriginPatterns("*") // Allow requests from all origins, or you can specify specific origins.
      .allowedMethods("*") // Allow HTTP methods: "GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS".
      .maxAge(Long.MAX_VALUE); // Set the maximum allowed time for cross-origin requests.
  }
}
