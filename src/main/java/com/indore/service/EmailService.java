package com.indore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    public String sendTestMail(String toEmail, String otp){
       SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
       message.setTo(toEmail);
       message.setSubject("Test Mail From Spring Boot ");
        message.setText("Your OTP is: " + otp);

       mailSender.send(message);

       return "mail send successfully";
    }
}
