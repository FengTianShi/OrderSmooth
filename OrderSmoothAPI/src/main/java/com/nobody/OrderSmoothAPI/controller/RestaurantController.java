package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.CreateRestaurantParam;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.service.RestaurantService;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PutMapping("/restaurant/logo")
  public ResponseEntity<String> updateRestaurantLogo(
    MultipartFile restaurantLogo,
    HttpServletRequest request
  ) {
    Owner owner = RequestUtils.getOwner(request);

    String newFileName = StringUtils.generateUUID();

    try (FileOutputStream fos = new FileOutputStream(imagePath + newFileName)) {
      fos.write(restaurantLogo.getBytes());

      String restaurantLogoAddress = imageURL + newFileName;
    } catch (IOException e) {}

    return null;
  }
}
