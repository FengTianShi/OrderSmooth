package com.nobody.OrderSmoothAPI.service;

import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class EmailService {

  private final Logger logger = LoggerFactory.getLogger(EmailService.class);

  private final JavaMailSender javaMailSender;

  private final String from;

  private static final String OTP_EMAIL_TEMPLATE_PATH =
    "/template/email/otp.html";

  public EmailService(
    JavaMailSender javaMailSender,
    @Value("${spring.mail.username}") String from
  ) {
    this.javaMailSender = javaMailSender;
    this.from = from;
  }

  public void sendHtmlEmail(String to, String subject, String htmlContent) {
    try {
      MimeMessage message = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(htmlContent, true);

      javaMailSender.send(message);
    } catch (MessagingException e) {
      logger.error("Failed to send HTML email", e);
      throw new RuntimeException("Failed to send HTML email", e);
    }
  }

  public void sendOTP(String to, String otp) {
    try {
      String subject = "PLEASE VERIFY YOUR EMAIL";

      ClassPathResource template = new ClassPathResource(
        OTP_EMAIL_TEMPLATE_PATH
      );
      byte[] contentBytes = FileCopyUtils.copyToByteArray(
        template.getInputStream()
      );
      String content = new String(contentBytes, StandardCharsets.UTF_8);

      content = content.replace("${otp}", otp);
      sendHtmlEmail(to, subject, content);
    } catch (Exception e) {
      logger.error("Failed to send OTP", e);
      throw new RuntimeException("Failed to send OTP", e);
    }
  }
}
