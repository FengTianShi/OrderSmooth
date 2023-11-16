package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.JwtUtils;
import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.dto.OwnerSigninParamDTO;
import com.nobody.OrderSmoothAPI.service.OwnerSigninService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  Logger logger = LoggerFactory.getLogger(OwnerSigninController.class);

  @Autowired
  private OwnerSigninService ownerSigninService;

  @PostMapping("/signin")
  public ResponseEntity<String> ownerSignin(
    @Valid @RequestBody OwnerSigninParamDTO ownerSigninParamDTO,
    HttpServletRequest request
  ) {
    String ipAddress = RequestUtils.getIpAdrress(request);
    if (StringUtils.isBlank(ipAddress)) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("IP ADDRESS REQUIRED");
    }
    ownerSigninParamDTO.setIpAddress(ipAddress);

    String deviceinfo = RequestUtils.getDeviceInfo(request);
    if (StringUtils.isBlank(deviceinfo)) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("DEVICE INFO REQUIRED");
    }
    ownerSigninParamDTO.setDeviceInfo(deviceinfo);

    String ownerToken = ownerSigninService.ownerSignin(ownerSigninParamDTO);
    if (ownerToken == null) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("EMAIL OR PASSWORD NOT CORRECT");
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(ownerToken);
  }

  @GetMapping("/is-signin")
  public ResponseEntity<String> isOwnerSignin(HttpServletRequest request) {
    String ownerToken = request.getHeader("Authorization");

    if (ownerToken == null || !ownerToken.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    ownerToken = ownerToken.substring(7);
    if (!JwtUtils.isValid(ownerToken)) {
      logger.info("Token invalid");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
