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

    // Generate OTP
    public String generateOtp(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()) != null) {
            return "Email already registered";
        }

        // generate OTP
        String otp = String.valueOf(1000 + new Random().nextInt(9000));

        PendingUser pendingUser = new PendingUser();
        pendingUser.setName(request.getName());
        pendingUser.setEmail(request.getEmail());
        pendingUser.setAge(request.getAge());
        pendingUser.setPassword(request.getPassword());
        pendingUser.setOtp(otp);
        pendingUser.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        pendingUserRepository.save(pendingUser);


        try {
            emailService.sendTestMail(request.getEmail(), otp);
        } catch (Exception e) {
            System.out.println("Mail sending failed: " + e.getMessage());
        }

        return "OTP Sent Successfully";
    }

    // Verify OTP
    public String verifyOtp(OtpVerifyRequest otpVerifyRequest) {

        PendingUser pendingUser = pendingUserRepository.findByEmail(otpVerifyRequest.getEmail());

        if (pendingUser == null) {
            return "User not found";
        }

        if (LocalDateTime.now().isAfter(pendingUser.getExpiryTime())) {
            return "OTP Expired";
        }

        if (!pendingUser.getOtp().equals(otpVerifyRequest.getOtp())) {
            return "Invalid OTP";
        }

        if (userRepository.findByEmail(pendingUser.getEmail()) != null) {
            return "User already registered";
        }

        User user = new User();
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