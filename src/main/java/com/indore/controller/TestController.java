package com.indore.controller;

import com.indore.dto.OtpVerifyRequest;
import com.indore.dto.RegisterRequest;
import com.indore.service.AuthService;
import com.indore.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthService authService;


    @GetMapping("/")
    public String home(){
        return "OTP Service Running";
    }

    @PostMapping("/GenrateOtp")
    public String generateOtp(@RequestBody RegisterRequest request){
        return authService.generateOtp(request);
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestBody OtpVerifyRequest otpVerifyRequest){
        return authService.verifyOtp(otpVerifyRequest);
    }

}