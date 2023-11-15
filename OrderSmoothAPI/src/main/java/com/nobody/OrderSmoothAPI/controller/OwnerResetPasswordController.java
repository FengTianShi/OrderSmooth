package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.JwtUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.ConfirmOPTParamDTO;
import com.nobody.OrderSmoothAPI.dto.OwnerResetPasswordParamDTO;
import com.nobody.OrderSmoothAPI.dto.OwnerResetPasswordTokenDTO;
import com.nobody.OrderSmoothAPI.service.EmailService;
import com.nobody.OrderSmoothAPI.service.OwnerService;
import io.jsonwebtoken.ExpiredJwtException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerResetPasswordController {

  Logger logger = LoggerFactory.getLogger(OwnerSignupController.class);

  @Value("${owner.reset-password-session.expiration}")
  private Long ownerResetPasswordSessionExpiration;

  @Autowired
  private OwnerService ownerService;

  @Autowired
  private EmailService emailService;

  @PostMapping("/reset-password")
  public ResponseEntity<String> ownerResetPassword(
    @Valid @RequestBody OwnerResetPasswordParamDTO ownerResetPasswordParamDTO,
    HttpServletRequest request
  ) {
    if (
      ownerService.getOwnerByEmail(
        ownerResetPasswordParamDTO.getOwnerEmail()
      ) ==
      null
    ) {
      return ResponseEntity.badRequest().body("OWNER NOT EXISTS");
    }

    String otp = StringUtils.generateOPT(6);

    try {
      String to = ownerResetPasswordParamDTO.getOwnerEmail();
      String subject = "PLEASE VERIFY YOUR EMAIL";

      ClassPathResource emailTemplate = new ClassPathResource(
        "/email-template-opt.html"
      );
      byte[] contentBytes = FileCopyUtils.copyToByteArray(
        emailTemplate.getInputStream()
      );
      String emailHtmlContent = new String(
        contentBytes,
        StandardCharsets.UTF_8
      );

      emailHtmlContent = emailHtmlContent.replace("${otp}", otp);
      emailService.sendHtmlEmail(to, subject, emailHtmlContent);
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("ERROR OCCURRED WHEN SENDING EMAIL");
    }

    return ResponseEntity.ok(
      JwtUtils.generateToken(
        "owner-reset-password-session-token",
        OwnerResetPasswordTokenDTO
          .builder()
          .ownerEmail(ownerResetPasswordParamDTO.getOwnerEmail())
          .newPassword(ownerResetPasswordParamDTO.getNewPassword())
          .otp(otp)
          .build(),
        ownerResetPasswordSessionExpiration
      )
    );
  }

  @PostMapping("/reset-password/confirm")
  public ResponseEntity<String> ownerResetPasswordConfirm(
    @Valid @RequestBody ConfirmOPTParamDTO confirmOPTParamDTO
  ) {
    try {
      OwnerResetPasswordTokenDTO ownerSignupTokenDTO = JwtUtils.getContent(
        confirmOPTParamDTO.getToken(),
        OwnerResetPasswordTokenDTO.class
      );

      if (!ownerSignupTokenDTO.getOtp().equals(confirmOPTParamDTO.getOtp())) {
        return ResponseEntity
          .status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("OPT NOT CORRECT");
      }

      if (
        ownerService.updateOwnerPasswordByEmail(
          OwnerResetPasswordParamDTO
            .builder()
            .ownerEmail(ownerSignupTokenDTO.getOwnerEmail())
            .newPassword(ownerSignupTokenDTO.getNewPassword())
            .build()
        ) >
        0
      ) {
        return ResponseEntity.ok("PASSWORD RESET SUCCESS");
      }

      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("PASSWORD RESET FAILED");
    } catch (ExpiredJwtException e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("TOKEN EXPIRED");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("INVALID TOKEN");
    }
  }
}
