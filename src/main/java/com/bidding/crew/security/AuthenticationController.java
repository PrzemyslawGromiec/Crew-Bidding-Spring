package com.bidding.crew.security;

import com.bidding.crew.user.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "1. Authentication",
        description = "Handles user registration and login using JWT-based authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register new user",
            description = "Creates a new user account with hashed password and returns user info"
    )
    public ResponseEntity<AccountUserDto> registerUser(@Valid @RequestBody RegistrationDto registrationDto) {
        AccountUserDto user = authenticationService.registerUser(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticates user and returns JWT token")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto) {
        LoginResponse loginResponse = authenticationService.signIn(loginDto, jwtService);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResponse);
    }

}

