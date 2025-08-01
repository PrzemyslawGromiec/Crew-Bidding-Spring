package com.bidding.crew.security;

import com.bidding.crew.general.AuthenticationException;
import com.bidding.crew.user.*;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AccountUserDto registerUser(RegistrationDto registrationDto) {
        if (userRepository.findByUsername(registrationDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());

        AccountUser newAccountUser = new AccountUser();
        newAccountUser.setUsername(registrationDto.getUsername());
        newAccountUser.setPassword(encodedPassword);
        newAccountUser.setRole(Role.ADMIN);
        AccountUser accountUser = userRepository.save(newAccountUser);
        return new AccountUserDto(accountUser.getUserId(), accountUser.getUsername(), accountUser.getRole());
    }

    public AccountUser authenticate(LoginDto input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new AuthenticationException("Invalid username or password");
        }

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(()-> new AuthenticationException("User not found"));
    }

    public LoginResponse signIn(LoginDto loginDto, JwtService jwtService) {
        AccountUser authenticatedUser = authenticate(loginDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return loginResponse;
    }

}