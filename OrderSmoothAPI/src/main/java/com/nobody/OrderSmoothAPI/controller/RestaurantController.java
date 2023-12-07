package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.CreateRestaurantParam;
import com.nobody.OrderSmoothAPI.dto.RestaurantDTO;
import com.nobody.OrderSmoothAPI.dto.UpdateRestaurantParam;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.entity.Restaurant;
import com.nobody.OrderSmoothAPI.entity.RestaurantImage;
import com.nobody.OrderSmoothAPI.service.RestaurantImageService;
import com.nobody.OrderSmoothAPI.service.RestaurantService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  private final RestaurantImageService restaurantImageService;

  private final String imagePath;

  private final String imageURL;

  private final Integer OWNER_RESTAURANT_IMAGE_MAX_NUMBER;

  public RestaurantController(
    RestaurantService restaurantService,
    RestaurantImageService restaurantImageService,
    @Value("${ordersmooth.image.path}") String imagePath,
    @Value("${ordersmooth.image.url}") String imageURL,
    @Value(
      "${owner.restaurant.image.max-number}"
    ) Integer OWNER_RESTAURANT_IMAGE_MAX_NUMBER
  ) {
    this.restaurantService = restaurantService;
    this.restaurantImageService = restaurantImageService;
    this.imagePath = imagePath;
    this.imageURL = imageURL;
    this.OWNER_RESTAURANT_IMAGE_MAX_NUMBER = OWNER_RESTAURANT_IMAGE_MAX_NUMBER;
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

  @GetMapping("/restaurant/{restaurantId}")
  public ResponseEntity<RestaurantDTO> getRestaurant(
    @PathVariable Long restaurantId
  ) {
    RestaurantDTO restaurant = restaurantService.getRestaurantFull(
      restaurantId
    );
    return ResponseEntity.status(HttpStatus.OK).body(restaurant);
  }

  @PutMapping("/restaurant/{restaurantId}")
  public ResponseEntity<Void> updateRestaurant(
    @PathVariable Long restaurantId,
    @Valid @RequestBody UpdateRestaurantParam updateRestaurantParam,
    HttpServletRequest request
  ) {
    Owner owner = RequestUtils.getOwner(request);

    try {
      restaurantService.updateRestaurant(restaurantId, updateRestaurantParam);
      logger.info(
        "Successfully updated restaurant, Owner Id : {}, Restaurant Id : {}",
        owner.getOwnerId(),
        restaurantId
      );
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      logger.error(
        "Failed to update restaurant, Owner Id : {}, Restaurant Id : {}",
        owner.getOwnerId(),
        restaurantId,
        e
      );
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/restaurant/{restaurantId}/logo")
  public ResponseEntity<String> getRestaurantLogo(
    @PathVariable Long restaurantId
  ) {
    Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(restaurant.getRestaurantLogoAddress());
  }

  @PutMapping("/restaurant/{restaurantId}/logo")
  public ResponseEntity<String> updateRestaurantLogo(
    @PathVariable Long restaurantId,
    @RequestBody MultipartFile restaurantLogo,
    HttpServletRequest request
  ) {
    Owner owner = RequestUtils.getOwner(request);

    String newFileName = getNewFileName(restaurantLogo);
    if (newFileName == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    try (FileOutputStream fos = new FileOutputStream(imagePath + newFileName)) {
      fos.write(restaurantLogo.getBytes());

      Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
      String originalRestaurantLogoAddress = restaurant.getRestaurantLogoAddress();

      String restaurantLogoAddress = imageURL + newFileName;
      restaurant.setRestaurantLogoAddress(restaurantLogoAddress);
      restaurantService.updateRestaurant(restaurant);

      if (!StringUtils.isEmpty(originalRestaurantLogoAddress)) {
        deleteFile(originalRestaurantLogoAddress);
      }

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

  @GetMapping("/restaurant/{restaurantId}/image")
  public ResponseEntity<List<RestaurantImage>> getRestaurantImage(
    @PathVariable Long restaurantId
  ) {
    List<RestaurantImage> restaurantImage = restaurantImageService.getAllRestaurantImage(
      restaurantId
    );
    return ResponseEntity.status(HttpStatus.OK).body(restaurantImage);
  }

  @PostMapping("/restaurant/{restaurantId}/image")
  public ResponseEntity<String> createRestaurantImage(
    @PathVariable Long restaurantId,
    @RequestBody MultipartFile restaurantImage,
    HttpServletRequest request
  ) {
    Owner owner = RequestUtils.getOwner(request);

    Integer restaurantImageCount = restaurantImageService
      .getRestaurantImageCount(restaurantId)
      .intValue();
    if (restaurantImageCount >= OWNER_RESTAURANT_IMAGE_MAX_NUMBER) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    String newFileName = getNewFileName(restaurantImage);
    if (newFileName == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    try (FileOutputStream fos = new FileOutputStream(imagePath + newFileName)) {
      fos.write(restaurantImage.getBytes());

      restaurantImageService.createRestaurantImage(
        restaurantId,
        RestaurantImage.builder().imageAddress(imageURL + newFileName).build()
      );

      logger.info(
        "Successfully created restaurant image, Owner Id : {}, Restaurant Id : {}",
        owner.getOwnerId(),
        restaurantId
      );
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (IOException e) {
      logger.error(
        "Failed to create restaurant image, Owner Id : {}, Restaurant Id : {}",
        owner.getOwnerId(),
        restaurantId,
        e
      );
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping("/restaurant/{restaurantId}/image/{imageId}")
  public ResponseEntity<Void> deleteRestaurantImage(
    @PathVariable Long imageId,
    HttpServletRequest request
  ) {
    Owner owner = RequestUtils.getOwner(request);

    try {
      RestaurantImage restaurantImage = restaurantImageService.getRestaurantImage(
        imageId
      );
      if (restaurantImage == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      if (!deleteFile(restaurantImage.getImageAddress())) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }

      restaurantImageService.deleteRestaurantImage(imageId);

      logger.info(
        "Successfully deleted restaurant image, Owner Id : {}, Image Id : {}",
        owner.getOwnerId(),
        imageId
      );
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      logger.error(
        "Failed to delete restaurant image, Owner Id : {}, Image Id : {}",
        owner.getOwnerId(),
        imageId,
        e
      );
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  private Boolean deleteFile(String fileAddress) {
    try {
      String fileName = fileAddress
        .substring(fileAddress.lastIndexOf("/"))
        .substring(1);
      File file = new File(imagePath + fileName);
      if (file.exists()) {
        file.delete();
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private String getNewFileName(MultipartFile file) {
    if (file == null) {
      return null;
    }

    String originalFileName = file.getOriginalFilename();
    if (originalFileName == null) {
      return null;
    }

    String originalFileType = originalFileName.substring(
      originalFileName.lastIndexOf(".")
    );
    if (
      StringUtils.isEmpty(originalFileType) ||
      (
        !originalFileType.equalsIgnoreCase(".jpg") &&
        !originalFileType.equalsIgnoreCase(".jpeg") &&
        !originalFileType.equalsIgnoreCase(".png")
      )
    ) {
      return null;
    }

    return StringUtils.generateUUID() + originalFileType;
  }

  @GetMapping("/restaurant/{restaurantId}/image/max-number")
  public ResponseEntity<Integer> getRestaurantImageMaxNumber() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(OWNER_RESTAURANT_IMAGE_MAX_NUMBER);
  }
}
