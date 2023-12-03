package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.CreateRestaurantParam;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.entity.Restaurant;
import com.nobody.OrderSmoothAPI.service.RestaurantService;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RestaurantController {

  private final Logger logger = LoggerFactory.getLogger(
    RestaurantController.class
  );

  private final RestaurantService restaurantService;

  private final String imagePath;

  private final String imageURL;

  public RestaurantController(
    RestaurantService restaurantService,
    @Value("${ordersmooth.image.path}") String imagePath,
    @Value("${ordersmooth.image.url}") String imageURL
  ) {
    this.restaurantService = restaurantService;
    this.imagePath = imagePath;
    this.imageURL = imageURL;
  }

  @PostMapping("/restaurant")
  public ResponseEntity<Long> createRestaurant(
    @Valid @RequestBody CreateRestaurantParam createRestaurantParam,
    HttpServletRequest request
  ) {
    Owner owner = RequestUtils.getOwner(request);

    try {
      Long restaurantId = restaurantService.createRestaurant(
        owner.getOwnerId(),
        createRestaurantParam
      );
      logger.info(
        "Successfully created restaurant, Owner Id : {}, Restaurant Id : {}",
        owner.getOwnerId(),
        restaurantId
      );
      return ResponseEntity.status(HttpStatus.CREATED).body(restaurantId);
    } catch (Exception e) {
      logger.error(
        "Failed to create restaurant, Owner Id : {}",
        owner.getOwnerId(),
        e
      );
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/restaurant/logo/{restaurantId}")
  public ResponseEntity<String> updateRestaurantLogo(
    @PathVariable Long restaurantId,
    @Valid @RequestBody @NotNull MultipartFile restaurantLogo,
    HttpServletRequest request
  ) {
    Owner owner = RequestUtils.getOwner(request);

    String originalFilename = restaurantLogo.getOriginalFilename();
    if (originalFilename == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    String newFileName =
      StringUtils.generateUUID() +
      originalFilename.substring(originalFilename.lastIndexOf("."));

    try (FileOutputStream fos = new FileOutputStream(imagePath + newFileName)) {
      fos.write(restaurantLogo.getBytes());

      Restaurant restaurant = restaurantService.getRestaurant(restaurantId);

      String restaurantLogoAddress = imageURL + newFileName;
      restaurant.setRestaurantLogoAddress(restaurantLogoAddress);
      restaurantService.updateRestaurant(restaurant);

      logger.info(
        "Successfully updated restaurant logo, Owner Id : {}, Restaurant Id : {}",
        owner.getOwnerId(),
        restaurantId
      );
      return ResponseEntity.status(HttpStatus.OK).body(restaurantLogoAddress);
    } catch (IOException e) {
      logger.error(
        "Failed to update restaurant logo, Owner Id : {}, Restaurant Id : {}",
        owner.getOwnerId(),
        restaurantId,
        e
      );
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
