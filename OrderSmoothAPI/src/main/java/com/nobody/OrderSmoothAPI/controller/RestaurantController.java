package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.CreateRestaurantParam;
import com.nobody.OrderSmoothAPI.dto.RestaurantDTO;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.entity.Restaurant;
import com.nobody.OrderSmoothAPI.service.RestaurantService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<RestaurantDTO> getRestaurant(
    @PathVariable Long restaurantId
  ) {
    RestaurantDTO restaurant = restaurantService.getRestaurantFullInfo(
      restaurantId
    );
    return ResponseEntity.status(HttpStatus.OK).body(restaurant);
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

  @GetMapping("/restaurant/logo/{restaurantId}")
  public ResponseEntity<String> getRestaurantLogo(
    @PathVariable Long restaurantId
  ) {
    Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(restaurant.getRestaurantLogoAddress());
  }

  @PutMapping("/restaurant/logo/{restaurantId}")
  public ResponseEntity<String> updateRestaurantLogo(
    @PathVariable Long restaurantId,
    @RequestBody MultipartFile restaurantLogo,
    HttpServletRequest request
  ) {
    Owner owner = RequestUtils.getOwner(request);

    if (restaurantLogo == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    String originalFilename = restaurantLogo.getOriginalFilename();
    if (originalFilename == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    String originalFileType = originalFilename.substring(
      originalFilename.lastIndexOf(".")
    );
    if (
      StringUtils.isEmpty(originalFileType) ||
      (
        !originalFileType.equalsIgnoreCase(".jpg") &&
        !originalFileType.equalsIgnoreCase(".jpeg") &&
        !originalFileType.equalsIgnoreCase(".png")
      )
    ) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    String newFileName = StringUtils.generateUUID() + originalFileType;

    try (FileOutputStream fos = new FileOutputStream(imagePath + newFileName)) {
      fos.write(restaurantLogo.getBytes());

      Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
      String originalRestaurantLogoAddress = restaurant.getRestaurantLogoAddress();

      String restaurantLogoAddress = imageURL + newFileName;
      restaurant.setRestaurantLogoAddress(restaurantLogoAddress);
      restaurantService.updateRestaurant(restaurant);

      logger.info(
        "Successfully updated restaurant logo, Owner Id : {}, Restaurant Id : {}",
        owner.getOwnerId(),
        restaurantId
      );

      // delete original logo
      try {
        if (!StringUtils.isEmpty(originalRestaurantLogoAddress)) {
          String originalFileName = originalRestaurantLogoAddress
            .substring(originalRestaurantLogoAddress.lastIndexOf("/"))
            .substring(1);
          File originalFile = new File(imagePath + originalFileName);
          if (originalFile.exists()) {
            originalFile.delete();
          }
        }
      } catch (Exception e) {
        logger.error(
          "Failed to delete original restaurant logo, Owner Id : {}, Restaurant Id : {}",
          owner.getOwnerId(),
          restaurantId,
          e
        );
      }

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
