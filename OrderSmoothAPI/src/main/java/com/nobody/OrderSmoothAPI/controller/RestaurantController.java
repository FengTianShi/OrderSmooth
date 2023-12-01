package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.dto.CreateRestaurantParam;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

  @PostMapping("/restaurant")
  public ResponseEntity<String> createRestaurant(
    @Valid @RequestBody CreateRestaurantParam createRestaurantParam
  ) {
    // System.out.println(createRestaurantParam);
    return ResponseEntity.ok("success");
  }
}
