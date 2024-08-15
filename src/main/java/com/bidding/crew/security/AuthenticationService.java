package com.bidding.crew.security;

import com.bidding.crew.user.*;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AccountUserDto registerUser(RegistrationDto registrationDto) {
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());

        AccountUser newAccountUser = new AccountUser();
        newAccountUser.setUsername(registrationDto.getUsername());
        newAccountUser.setPassword(encodedPassword);
        newAccountUser.setRole(Role.USER);
        AccountUser accountUser = userRepository.save(newAccountUser);

        return new AccountUserDto(accountUser.getUserId(),accountUser.getUsername(),accountUser.getRole());

    }

}
