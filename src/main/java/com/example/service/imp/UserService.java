package com.example.service.imp;

import com.example.entity.AppUser;
import com.example.entity.VerificationToken;
import com.example.exception.UserAlreadyExistsException;
import com.example.registration.RegisterationRequest;
import com.example.repository.UserRepository;
import com.example.repository.VerificationTokenRepository;
import com.example.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService tokenService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public AppUser registerUser(RegisterationRequest request) {
        Optional<AppUser> user = this.findByEmail(request.email());
        if (user.isPresent()){
            throw new UserAlreadyExistsException(
                    "User with email "+request.email() + " already exists");
        }
        var newUser = new AppUser();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        return userRepository.save(newUser);

    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(AppUser theUser, String verificationToken) {
        VerificationToken token=new VerificationToken(verificationToken, theUser);
        verificationTokenRepository.save(token);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenService.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        AppUser user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenService.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
}
