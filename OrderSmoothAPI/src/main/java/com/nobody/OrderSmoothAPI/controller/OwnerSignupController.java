package com.nobody.OrderSmoothAPI.controller;

import com.nobody.OrderSmoothAPI.common.JwtUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.ConfirmOTPParamDTO;
import com.nobody.OrderSmoothAPI.dto.OwnerSignupParamDTO;
import com.nobody.OrderSmoothAPI.dto.OwnerSignupTokenDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerSignupController {

  Logger logger = LoggerFactory.getLogger(OwnerSignupController.class);

  @Value("${owner.signup.expiration}")
  private Long ownerSignupExpiration;

  @Autowired
  private OwnerService ownerService;

  @Autowired
  private EmailService emailService;

  @PostMapping("/signup")
  public ResponseEntity<String> ownerSignup(
    @Valid @RequestBody OwnerSignupParamDTO ownerSignupParamDTO,
    HttpServletRequest request
  ) {
    if (isOwnerExists(ownerSignupParamDTO.getOwnerEmail())) {
      return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body("OWNER EMAIL DUPLICATED");
    }

    String otp = StringUtils.generateOTP(6);

    try {
      String to = ownerSignupParamDTO.getOwnerEmail();
      String subject = "PLEASE VERIFY YOUR EMAIL";

      ClassPathResource emailTemplate = new ClassPathResource(
        "/email-template-otp.html"
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
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        JwtUtils.generateToken(
          "owner-signup-token",
          OwnerSignupTokenDTO
            .builder()
            .ownerName(ownerSignupParamDTO.getOwnerName())
            .ownerEmail(ownerSignupParamDTO.getOwnerEmail())
            .ownerPassword(ownerSignupParamDTO.getOwnerPassword())
            .otp(otp)
            .build(),
          ownerSignupExpiration
        )
      );
  }

  @PostMapping("/signup/confirm")
  public ResponseEntity<String> ownerSignupConfirm(
    @Valid @RequestBody ConfirmOTPParamDTO confirmOTPParamDTO
  ) {
    try {
      OwnerSignupTokenDTO ownerSignupTokenDTO = JwtUtils.getContent(
        confirmOTPParamDTO.getToken(),
        OwnerSignupTokenDTO.class
      );

      if (!ownerSignupTokenDTO.getOtp().equals(confirmOTPParamDTO.getOtp())) {
        return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body("OTP NOT CORRECT");
      }

      if (
        ownerService.createOwner(
          OwnerSignupParamDTO
            .builder()
            .ownerName(ownerSignupTokenDTO.getOwnerName())
            .ownerEmail(ownerSignupTokenDTO.getOwnerEmail())
            .ownerPassword(ownerSignupTokenDTO.getOwnerPassword())
            .build()
        ) >
        0
      ) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    } catch (ExpiredJwtException e) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("TOKEN EXPIRED");
    } catch (Exception e) {
      return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body("TOKEN INVALID");
    }
  }

  @GetMapping("/is-exists/{email}")
  public Boolean isOwnerExists(@PathVariable String email) {
    return ownerService.getOwnerByEmail(email) != null;
  }
}
