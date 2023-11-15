package com.nobody.OrderSmoothAPI.controller;

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

import com.nobody.OrderSmoothAPI.common.JwtUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.ConfirmOPTParamDTO;
import com.nobody.OrderSmoothAPI.dto.OwnerSignupParamDTO;
import com.nobody.OrderSmoothAPI.dto.OwnerSignupTokenDTO;
import com.nobody.OrderSmoothAPI.service.EmailService;
import com.nobody.OrderSmoothAPI.service.OwnerService;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping("/owner")
public class OwnerSignupController {

    Logger logger = LoggerFactory.getLogger(OwnerSignupController.class);

    @Value("${owner.signup-session.expiration}")
    private Long ownerSignupSessionExpiration;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<String> ownerSignup(
            @Valid @RequestBody OwnerSignupParamDTO ownerSignupParamDTO,
            HttpServletRequest request) {

        if (isOwnerEmailExists(ownerSignupParamDTO.getOwnerEmail()))
            return ResponseEntity
                    .badRequest()
                    .body("EMAIL DUPLICATED");

        String otp = StringUtils.generateOPT(6);

        try {
            String to = ownerSignupParamDTO.getOwnerEmail();
            String subject = "PLEASE VERIFY YOUR EMAIL";

            ClassPathResource emailTemplate = new ClassPathResource("/email-template-opt.html");
            byte[] contentBytes = FileCopyUtils.copyToByteArray(emailTemplate.getInputStream());
            String emailHtmlContent = new String(contentBytes, StandardCharsets.UTF_8);

            emailHtmlContent = emailHtmlContent.replace("${otp}", otp);
            emailService.sendHtmlEmail(to, subject, emailHtmlContent);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR OCCURRED WHEN SENDING EMAIL");
        }

        return ResponseEntity.ok(
                JwtUtils.generateToken(
                        "owner-signup-session-token",
                        OwnerSignupTokenDTO.builder()
                                .ownerName(ownerSignupParamDTO.getOwnerName())
                                .ownerEmail(ownerSignupParamDTO.getOwnerEmail())
                                .ownerPassword(ownerSignupParamDTO.getOwnerPassword())
                                .otp(otp)
                                .build(),
                        ownerSignupSessionExpiration));
    }

    @PostMapping("/signup/confirm")
    public ResponseEntity<String> ownerSignupConfirm(
            @Valid @RequestBody ConfirmOPTParamDTO confirmOPTParamDTO) {

        try {
            OwnerSignupTokenDTO ownerSignupTokenDTO = JwtUtils.getContent(
                    confirmOPTParamDTO.getToken(),
                    OwnerSignupTokenDTO.class);

            if (!ownerSignupTokenDTO.getOtp().equals(confirmOPTParamDTO.getOtp()))
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("OPT NOT CORRECT");

            if (ownerService.insertOwner(
                    OwnerSignupParamDTO
                            .builder()
                            .ownerName(ownerSignupTokenDTO.getOwnerName())
                            .ownerEmail(ownerSignupTokenDTO.getOwnerEmail())
                            .ownerPassword(ownerSignupTokenDTO.getOwnerPassword())
                            .build()) > 0)
                return ResponseEntity.ok("OWNER CREATED");

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("OWNER CREATION FAILED");

        } catch (ExpiredJwtException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("TOKEN EXPIRED");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("INVALID TOKEN");
        }
    }

    @GetMapping("/{email}")
    public Boolean isOwnerEmailExists(@PathVariable String email) {
        return ownerService.getOwnerByEmail(email) != null;
    }

}
