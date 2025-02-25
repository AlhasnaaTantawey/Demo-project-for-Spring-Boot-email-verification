package com.example.controller;

import com.example.entity.AppUser;
import com.example.entity.VerificationToken;
import com.example.event.RegistrationCompleteEvent;
import com.example.registration.RegisterationRequest;
import com.example.service.imp.UserService;
import com.example.service.imp.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final   UserService userService;
    private final ApplicationEventPublisher publisher;
   private final VerificationTokenService tokenService;

    @PostMapping
    public String registerUser(@RequestBody RegisterationRequest registrationRequest, final HttpServletRequest request){
        AppUser user = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success!  Please, check your email  to complete your registration";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenService.findByToken(token);
        if (theToken.getUser().isEnabled()){
            return "This account has already been verified, please, login.";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification token";
    }



    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
