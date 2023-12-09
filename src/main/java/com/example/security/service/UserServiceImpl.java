package com.example.security.service;

import com.example.security.model.User;
import com.example.security.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(int id) {
        log.info("Starting method findById in class: " + getClass());
        User theUser = null;
        try {
            log.info("Finding user with id : " + id);
            User user = userRepository.findById(id).orElseThrow();
            log.info("User found!!");
            theUser = user;
            log.info("End of the method findById in class: " + getClass());
            return theUser;
        } catch (EntityNotFoundException eN) {
            log.error("Can't find the user");
            return theUser;
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User save(User user) {
        log.info("Starting method save in class: " + getClass());
        User theUser = null;
        try {
            log.info("Saving the User :");
            theUser = userRepository.save(user);
            log.info("The user is successfully saved!");
            log.info("End of the method save in class: " + getClass());
            return theUser;
        } catch (NullPointerException e) {
            log.error("Can't save the user on database!");
            return theUser;
        }
    }

    @Override
    public User loadUserById(String id) {
        Optional<User> user = userRepository.findById(Integer.valueOf(id));
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return user.get();
    }

}