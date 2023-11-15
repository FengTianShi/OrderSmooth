// package com.nobody.OrderSmoothAPI.controller;

// import java.nio.charset.StandardCharsets;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpSession;
// import javax.validation.Valid;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.util.FileCopyUtils;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import com.nobody.OrderSmoothAPI.common.StringUtils;
// import com.nobody.OrderSmoothAPI.dto.OwnerResetPasswordParamDTO;
// import com.nobody.OrderSmoothAPI.dto.VerifyOwnerResetPasswordParamDTO;
// import com.nobody.OrderSmoothAPI.dto.common.ResultDTO;
// import com.nobody.OrderSmoothAPI.service.EmailService;
// import com.nobody.OrderSmoothAPI.service.OwnerService;

// @RestController
// public class OwnerResetPasswordController {

//     Logger logger = LoggerFactory.getLogger(OwnerResetPasswordController.class);

//     @Autowired
//     private OwnerService ownerService;

//     @Autowired
//     private EmailService emailService;

//     @Value("${owner.reset-password-session.holding-seconds}")
//     private Integer ownerResetPasswordSessionHoldingSeconds;

//     @PostMapping("/owner/password/reset-session")
//     public ResultDTO createOwnerPasswordResetSession(
//             @Valid @RequestBody OwnerResetPasswordParamDTO createOwnerPasswordResetDTO,
//             HttpServletRequest request) {

//         String verifyCode = StringUtils.generateRandomCode(6);
//         createOwnerPasswordResetDTO.setVerifyCode(verifyCode);

//         try {
//             String to = createOwnerPasswordResetDTO.getOwnerEmail();
//             String subject = "Please Verify Your Email";

//             ClassPathResource emailTemplate = new ClassPathResource("/email-template-verifycode.html");
//             byte[] contentBytes = FileCopyUtils.copyToByteArray(emailTemplate.getInputStream());
//             String emailHtmlContent = new String(contentBytes, StandardCharsets.UTF_8);

//             emailHtmlContent = emailHtmlContent.replace("${verifyCode}", verifyCode);
//             emailService.sendHtmlEmail(to, subject, emailHtmlContent);

//         } catch (Exception e) {
//             return ResultDTO.builder()
//                     .code(500)
//                     .message("Error occurred when sending email")
//                     .build();
//         }

//         HttpSession sessoin = request.getSession();
//         sessoin.setAttribute("owner-reset-password-session", createOwnerPasswordResetDTO);
//         sessoin.setMaxInactiveInterval(ownerResetPasswordSessionHoldingSeconds);

//         return ResultDTO.builder()
//                 .code(201)
//                 .message("Owner reset password session created")
//                 .build();
//     }

//     @PutMapping("owner/password")
//     public ResultDTO updateOwnerPassword(
//             @Valid @RequestBody VerifyOwnerResetPasswordParamDTO updateOwnerPasswordDTO,
//             HttpServletRequest request) {

//         HttpSession sessoin = request.getSession();
//         OwnerResetPasswordParamDTO createOwnerPasswordResetDTO = (OwnerResetPasswordParamDTO) sessoin
//                 .getAttribute("owner-reset-password-session");

//         if (createOwnerPasswordResetDTO == null) {
//             return ResultDTO.builder()
//                     .code(404)
//                     .message("Owner reset password session not found")
//                     .build();
//         }

//         if (!createOwnerPasswordResetDTO.getVerifyCode().equals(updateOwnerPasswordDTO.getVerifyCode())) {
//             return ResultDTO.builder()
//                     .code(500)
//                     .message("Verify code not correct")
//                     .build();
//         }

//         sessoin.removeAttribute("owner-reset-password-session");

//         updateOwnerPasswordDTO.setOwnerEmail(createOwnerPasswordResetDTO.getOwnerEmail());
//         updateOwnerPasswordDTO.setNewPassword(createOwnerPasswordResetDTO.getNewPassword());

//         if (ownerService.updateOwnerPasswordByEmail(updateOwnerPasswordDTO) > 0) {
//             return ResultDTO.builder()
//                     .code(201)
//                     .message("Owner password update success")
//                     .build();
//         }

//         return ResultDTO.builder()
//                 .code(404)
//                 .message("Owner not found")
//                 .build();
//     }

// }
