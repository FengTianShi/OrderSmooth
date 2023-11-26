package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.dto.OwnerSigninParam;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.service.OwnerService;
import com.nobody.OrderSmoothAPI.service.OwnerSigninService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerSigninController {

  private final Logger logger = LoggerFactory.getLogger(
    OwnerSigninController.class
  );

  private OwnerSigninService ownerSigninService;

  private OwnerService ownerService;

  public OwnerSigninController(
    OwnerSigninService ownerSigninService,
    OwnerService ownerService
  ) {
    this.ownerSigninService = ownerSigninService;
    this.ownerService = ownerService;
  }

  @PostMapping("/signin")
  public ResponseEntity<String> ownerSignin(
    @Valid @RequestBody OwnerSigninParam ownerSigninParam,
    HttpServletRequest request
  ) {
    String ipAddress = RequestUtils.getIpAddress(request);
    if (StringUtils.isBlank(ipAddress)) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("IP Address required");
    }
    ownerSigninParam.setIpAddress(ipAddress);

    String deviceinfo = RequestUtils.getDevice(request);
    if (StringUtils.isBlank(deviceinfo)) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("Device information required");
    }
    ownerSigninParam.setDeviceInfo(deviceinfo);

    String ownerToken = ownerSigninService.ownerSignin(ownerSigninParam);
    if (ownerToken == null) {
      logger.info("Owner {} signin failed", ownerSigninParam.getOwnerEmail());
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Email or password not correct");
    }

    logger.info("Owner {} signin", ownerSigninParam.getOwnerEmail());
    return ResponseEntity.status(HttpStatus.CREATED).body(ownerToken);
  }

  @GetMapping("/is-signin")
  public ResponseEntity<String> isOwnerSignin(HttpServletRequest request) {
    Owner owner = RequestUtils.getOwner(request);
    if (owner == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    if (ownerService.getOwnerById(owner.getOwnerId()) == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
