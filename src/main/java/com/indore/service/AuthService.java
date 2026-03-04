package com.indore.service;

import com.indore.dto.OtpVerifyRequest;
import com.indore.dto.RegisterRequest;
import com.indore.entity.PendingUser;
import com.indore.entity.User;
import com.indore.repository.PendingUserRepository;
import com.indore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private EmailService emailService;
    @Autowired
    private PendingUserRepository pendingUserRepository;
    @Autowired
    private UserRepository userRepository;

    //generateOtp
     public String generateOtp(RegisterRequest request){
         if (userRepository.findByEmail(request.getEmail()) != null) {
             return "Email already registered";
         }
     //generating the otp
         String otp = String.valueOf(1000 + new Random().nextInt(9000));

         com.indore.entity.PendingUser pendingUser = new PendingUser();
         pendingUser.setName(request.getName());
         pendingUser.setEmail(request.getEmail());
         pendingUser.setAge(request.getAge());
         pendingUser.setPassword(request.getPassword());
         pendingUser.setOtp(otp);
         pendingUser.setExpiryTime(LocalDateTime.now().plusMinutes(5));

         pendingUserRepository.save(pendingUser);
         emailService.sendTestMail(request.getEmail(),otp);
         return "OTP Sent Successfully";
     }

      public String verifyOtp(OtpVerifyRequest otpVerifyRequest){
         PendingUser pendingUser= pendingUserRepository.findByEmail(otpVerifyRequest.getEmail());

         if(pendingUser == null){
             return "user not found";
         }
         if(LocalDateTime.now().isAfter(pendingUser.getExpiryTime())){
             return "Otp Expired";
         }
         if(!pendingUser.getOtp().equals(otpVerifyRequest.getOtp())){
             return "Invalid otp";
         }
          if(userRepository.findByEmail(pendingUser.getEmail()) != null){
              return "User already registered";
          }
         com.indore.entity.User user = new User();
         user.setName(pendingUser.getName());
         user.setEmail(pendingUser.getEmail());
         user.setAge(pendingUser.getAge());
         user.setPassword(pendingUser.getPassword());
         user.setCreateAt(LocalDate.now());
         user.setRole("User");
         userRepository.save(user);
          pendingUserRepository.delete(pendingUser);
          return "Registration Successful";

      }

}
