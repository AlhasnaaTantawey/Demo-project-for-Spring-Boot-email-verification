package com.example.service;

import com.example.entity.AppUser;
import com.example.entity.VerificationToken;
import com.example.registration.RegisterationRequest;

import java.util.List;
import java.util.Optional;

public interface IVerificationTokenService {
    VerificationToken findByToken(String token);

    void delete(VerificationToken token);
}
