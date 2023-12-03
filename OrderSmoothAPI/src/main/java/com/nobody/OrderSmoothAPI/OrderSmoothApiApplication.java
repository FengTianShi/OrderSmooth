package com.nobody.OrderSmoothAPI;

import com.nobody.OrderSmoothAPI.config.GlobalCorsConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GlobalCorsConfig.class)
@MapperScan("com.nobody.OrderSmoothAPI.mapper")
public class OrderSmoothApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderSmoothApiApplication.class, args);
  }
}
