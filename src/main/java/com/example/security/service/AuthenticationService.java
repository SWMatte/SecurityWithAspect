package com.example.security.service;

import com.example.security.model.User;
import com.example.security.model.dto.AuthenticationRequest;
import com.example.security.model.dto.RegisterRequest;
import com.example.security.model.dto.RegisterResponse;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.security.model.dto.AuthenticationResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public RegisterResponse register(RegisterRequest request) throws Exception {
        log.info("Starting method register in class: " + getClass());
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRole())
                .build();
        try {
            log.info("Starting save the user...");
            userRepository.save(user);
            log.info("User saved successfully!");
            return new RegisterResponse(true);
        } catch (Exception e) {
            log.error("Can't save the User: " + e.getMessage());
            return new RegisterResponse(false);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Looking for the user");
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Bad credential");
        }
        log.info("Calling method Generate Token in JwtService");
        var jwtToken = jwtService.generateToken(user);
        log.info("The token is generated and the user is authenticated!");
        log.info(String.valueOf(user));
        return AuthenticationResponse.builder().token(jwtToken).message("Login successful!").build();
    }
}
