package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.dto.CreateRestaurantParam;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.service.RestaurantService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

  private final Logger logger = LoggerFactory.getLogger(
    RestaurantController.class
  );

  private final RestaurantService restaurantService;

  public RestaurantController(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @PostMapping("/restaurant")
  public ResponseEntity<String> createRestaurant(
    @Valid @RequestBody CreateRestaurantParam createRestaurantParam,
    HttpServletRequest request
  ) {
    // Owner owner = RequestUtils.getOwner(request);

    Owner owner = Owner.builder().ownerId(1L).build();

    try {
      restaurantService.createRestaurant(
        owner.getOwnerId(),
        createRestaurantParam
      );
      logger.info(
        "Successfully created restaurant Owner Id : {}",
        owner.getOwnerId()
      );
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      logger.error(
        "Failed to create restaurant Owner Id : {}",
        owner.getOwnerId(),
        e
      );
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
