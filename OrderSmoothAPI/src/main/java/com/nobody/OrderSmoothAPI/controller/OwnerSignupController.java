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
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import com.nobody.OrderSmoothAPI.common.StringUtils;
// import com.nobody.OrderSmoothAPI.dto.param.OwnerVrifySignupParamDTO;
// import com.nobody.OrderSmoothAPI.dto.OwnerSignupParamDTO;
// import com.nobody.OrderSmoothAPI.dto.common.ResultDTO;
// import com.nobody.OrderSmoothAPI.service.EmailService;
// import com.nobody.OrderSmoothAPI.service.OwnerService;

// @RestController
// public class OwnerSignupController {

//     Logger logger = LoggerFactory.getLogger(OwnerSignupController.class);

//     @Autowired
//     private OwnerService ownerService;

//     @Autowired
//     private EmailService emailService;

//     @Value("${owner.signup-session.holding-seconds}")
//     private Integer ownerSignupSessionHoldingSeconds;

//     @PostMapping("/owner/signup-session")
//     public ResultDTO createOwnerSignupSession(
//             @Valid @RequestBody OwnerSignupParamDTO createOwnerSignupDTO,
//             HttpServletRequest request) {

//         if (ownerService.getOwnerByEmail(createOwnerSignupDTO.getOwnerEmail()) != null) {
//             return ResultDTO.builder()
//                     .code(500)
//                     .message("The email is duplicated")
//                     .build();
//         }

//         String verifyCode = StringUtils.generateRandomCode(6);
//         createOwnerSignupDTO.setVerifyCode(verifyCode);

//         try {
//             String to = createOwnerSignupDTO.getOwnerEmail();
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
//         sessoin.setAttribute("owner-signup-session", createOwnerSignupDTO);
//         sessoin.setMaxInactiveInterval(ownerSignupSessionHoldingSeconds);

//         return ResultDTO.builder()
//                 .code(201)
//                 .message("Owner signup session created")
//                 .build();
//     }

//     @PostMapping("/owner")
//     public ResultDTO createOwner(
//             @Valid @RequestBody OwnerVrifySignupParamDTO createOwnerDTO,
//             HttpServletRequest request) {

//         HttpSession sessoin = request.getSession();
//         OwnerSignupParamDTO createOwnerSignupDTO = (OwnerSignupParamDTO) sessoin.getAttribute("owner-signup-session");

//         if (createOwnerSignupDTO == null) {
//             return ResultDTO.builder()
//                     .code(404)
//                     .message("Owner signup session not found")
//                     .build();
//         }

//         if (!createOwnerSignupDTO.getVerifyCode().equals(createOwnerDTO.getVerifyCode())) {
//             return ResultDTO.builder()
//                     .code(500)
//                     .message("Verify code not correct")
//                     .build();
//         }

//         sessoin.removeAttribute("owner-signup-session");

//         createOwnerDTO.setOwnerEmail(createOwnerSignupDTO.getOwnerEmail());
//         createOwnerDTO.setOwnerName(createOwnerSignupDTO.getOwnerName());
//         createOwnerDTO.setOwnerPassword(createOwnerSignupDTO.getOwnerPassword());

//         if (ownerService.createOwner(createOwnerDTO) > 0) {
//             return ResultDTO.builder()
//                     .code(201)
//                     .message("Owner created")
//                     .build();
//         }

//         return ResultDTO.builder()
//                 .code(500)
//                 .message("Owner create failure")
//                 .build();

//     }

//     @GetMapping("/owner-exists/{email}")
//     public Boolean checkOwnerExists(@PathVariable String email) {
//         return ownerService.getOwnerByEmail(email) != null;
//     }

// }
