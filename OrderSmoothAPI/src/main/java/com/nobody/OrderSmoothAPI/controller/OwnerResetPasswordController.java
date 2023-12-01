package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.JwtUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.ConfirmOTPParam;
import com.nobody.OrderSmoothAPI.dto.OwnerResetPasswordParam;
import com.nobody.OrderSmoothAPI.dto.OwnerResetPasswordToken;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerResetPasswordController {

  private final Logger logger = LoggerFactory.getLogger(
    OwnerResetPasswordController.class
  );

  private final OwnerService ownerService;

  private final EmailService emailService;

  private final Long OWNER_RESET_PASSWORD_EXPIRATION;

  private static final String OWNER_RESET_PASSWORD_TOKEN_SUBJECT =
    "owner-reset-password-token";

  public OwnerResetPasswordController(
    OwnerService ownerService,
    EmailService emailService,
    @Value(
      "${owner.reset-password.expiration}"
    ) Long OWNER_RESET_PASSWORD_EXPIRATION
  ) {
    this.ownerService = ownerService;
    this.emailService = emailService;
    this.OWNER_RESET_PASSWORD_EXPIRATION = OWNER_RESET_PASSWORD_EXPIRATION;
  }

  @PostMapping("/reset-password")
  public ResponseEntity<String> ownerResetPassword(
    @Valid @RequestBody OwnerResetPasswordParam ownerResetPasswordParam,
    HttpServletRequest request
  ) {
    try {
      String otp = StringUtils.generateOTP(6);
      emailService.sendOTP(ownerResetPasswordParam.getOwnerEmail(), otp);

      return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(
          JwtUtils.generate(
            OWNER_RESET_PASSWORD_TOKEN_SUBJECT,
            OwnerResetPasswordToken
              .builder()
              .ownerEmail(ownerResetPasswordParam.getOwnerEmail())
              .newPassword(ownerResetPasswordParam.getNewPassword())
              .otp(otp)
              .build(),
            OWNER_RESET_PASSWORD_EXPIRATION
          )
        );
    } catch (Exception e) {
      logger.error("Failed to send OTP", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/reset-password/confirm")
  public ResponseEntity<String> ownerResetPasswordConfirm(
    @Valid @RequestBody ConfirmOTPParam confirmOTPParam
  ) {
    try {
      OwnerResetPasswordToken ownerResetPasswordToken = JwtUtils.getContent(
        confirmOTPParam.getToken(),
        OwnerResetPasswordToken.class
      );

      if (!ownerResetPasswordToken.getOtp().equals(confirmOTPParam.getOtp())) {
        return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body("OTP Not correct");
      }

      try {
        ownerService.updateOwnerPasswordByEmail(
          OwnerResetPasswordParam
            .builder()
            .ownerEmail(ownerResetPasswordToken.getOwnerEmail())
            .newPassword(ownerResetPasswordToken.getNewPassword())
            .build()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      } catch (Exception e) {
        logger.error("Failed to update owner password");
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
}
