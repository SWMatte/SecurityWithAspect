package com.example.security.controller;

import com.example.security.aspect.Authorized;
import com.example.security.model.User;
import com.example.security.model.UserRole;
import com.example.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @Authorized(roles = {UserRole.ADMIN})
    public String getUser() {
        return "ciao";
    }


}