package com.example.security.controller;

import com.example.security.aspect.Authorized;
import com.example.security.model.UserRole;
import com.example.security.model.dto.AuthenticationRequest;
import com.example.security.model.dto.AuthenticationResponse;
import com.example.security.model.dto.RegisterRequest;
import com.example.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            log.info("Trying to register the user!");
            return ResponseEntity.ok(authenticationService.register(request));
        } catch (Exception e) {
            log.error("The registration failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        log.info("Trying to authenticate the user!");
        try {
            AuthenticationResponse authorized = authenticationService.authenticate(request);
            return ResponseEntity.ok(authorized);
        } catch (Exception e) {
            log.error("User unauthorized! Message: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}