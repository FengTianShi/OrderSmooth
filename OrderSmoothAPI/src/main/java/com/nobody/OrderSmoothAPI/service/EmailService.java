package com.nobody.OrderSmoothAPI.service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

  @Resource
  private JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String from;

  public void sendHtmlEmail(String to, String subject, String htmlContent)
    throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setFrom(from);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(htmlContent, true);

    javaMailSender.send(message);
  }
}
