package com.example.service.imp;

import com.example.entity.VerificationToken;
import com.example.repository.VerificationTokenRepository;
import com.example.service.IVerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenService implements IVerificationTokenService {

    private final VerificationTokenRepository vTokenRepo;

    @Override
    public VerificationToken findByToken(String token) {
        return vTokenRepo.findByToken(token);
    }

    @Override
    public void delete(VerificationToken token) {
         vTokenRepo.delete(token);
    }
}
