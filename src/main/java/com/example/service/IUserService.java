package com.example.service;

import com.example.entity.AppUser;
import com.example.registration.RegisterationRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<AppUser> getUsers();
    AppUser registerUser(RegisterationRequest request);
    Optional<AppUser> findByEmail(String email);

    void saveUserVerificationToken(AppUser theUser, String verificationToken);

    String validateToken(String theToken);
}
