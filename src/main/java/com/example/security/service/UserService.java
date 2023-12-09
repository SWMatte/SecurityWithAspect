package com.example.security.service;

import com.example.security.model.User;

import java.util.Optional;

public interface UserService {
    User findById(int id);
    Optional<User> findByEmail(String email);
    User save(User user);
    User loadUserById(String id);
 }