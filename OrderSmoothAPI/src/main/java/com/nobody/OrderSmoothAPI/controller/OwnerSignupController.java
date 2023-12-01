package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.JwtUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.ConfirmOTPParam;
import com.nobody.OrderSmoothAPI.dto.OwnerSignupParam;
import com.nobody.OrderSmoothAPI.dto.OwnerSignupToken;
import com.nobody.OrderSmoothAPI.service.EmailService;
import com.nobody.OrderSmoothAPI.service.OwnerService;
import io.jsonwebtoken.ExpiredJwtException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerSignupController {

  private final Logger logger = LoggerFactory.getLogger(
    OwnerSignupController.class
  );

  private final OwnerService ownerService;

  private final EmailService emailService;

  private final Long OWNER_SIGNUP_EXPIRATION;

  private static final String OWNER_SIGNUP_TOKEN_SUBJECT = "owner-signup-token";

  public OwnerSignupController(
    OwnerService ownerService,
    EmailService emailService,
    @Value("${owner.signup.expiration}") Long OWNER_SIGNUP_EXPIRATION
  ) {
    this.ownerService = ownerService;
    this.emailService = emailService;
    this.OWNER_SIGNUP_EXPIRATION = OWNER_SIGNUP_EXPIRATION;
  }

  @PostMapping("/signup")
  public ResponseEntity<String> ownerSignup(
    @Valid @RequestBody OwnerSignupParam ownerSignupParam,
    HttpServletRequest request
  ) {
    if (isOwnerExists(ownerSignupParam.getOwnerEmail())) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body("Owner email duplicated");
    }

    try {
      String otp = StringUtils.generateOTP(6);
      emailService.sendOTP(ownerSignupParam.getOwnerEmail(), otp);

      return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(
          JwtUtils.generate(
            OWNER_SIGNUP_TOKEN_SUBJECT,
            OwnerSignupToken
              .builder()
              .ownerName(ownerSignupParam.getOwnerName())
              .ownerEmail(ownerSignupParam.getOwnerEmail())
              .ownerPassword(ownerSignupParam.getOwnerPassword())
              .otp(otp)
              .build(),
            OWNER_SIGNUP_EXPIRATION
          )
        );
    } catch (Exception e) {
      logger.error("Failed to send OTP", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/signup/confirm")
  public ResponseEntity<String> ownerSignupConfirm(
    @Valid @RequestBody ConfirmOTPParam confirmOTPParam
  ) {
    try {
      OwnerSignupToken ownerSignupToken = JwtUtils.getContent(
        confirmOTPParam.getToken(),
        OwnerSignupToken.class
      );

      if (!ownerSignupToken.getOtp().equals(confirmOTPParam.getOtp())) {
        return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body("OTP Not correct");
      }

      try {
        ownerService.createOwner(
          OwnerSignupParam
            .builder()
            .ownerName(ownerSignupToken.getOwnerName())
            .ownerEmail(ownerSignupToken.getOwnerEmail())
            .ownerPassword(ownerSignupToken.getOwnerPassword())
            .build()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
      } catch (Exception e) {
        logger.error("Failed to create owner");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    } catch (ExpiredJwtException e) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Token expired");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("Token invalid");
    }
  }

  @GetMapping("/is-exists/{email}")
  public Boolean isOwnerExists(@PathVariable String email) {
    return ownerService.getOwnerByEmail(email) != null;
  }
}
