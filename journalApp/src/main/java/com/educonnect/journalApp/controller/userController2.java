package com.educonnect.journalApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.service.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.Authenticator;
import java.util.List;

@RestController
@RequestMapping("/user")
public class userController2 {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String testing() {
        return "Working ";
    }

    // @GetMapping("/test")
    // public String test() {
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // String authenticatedUserName = authentication.getName();
    // return authenticatedUserName;
    // }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        System.out.println("Update method");

        // Retrieve the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserName = authentication.getName();

        System.out.println("Authenticated User: " + authenticatedUserName);

        // Find the old user from the database
        User oldUser = userService.findByUser(authenticatedUserName);
        if (oldUser != null) {
            // Update the username
            if (user.getUserName() != null && !user.getUserName().isEmpty()) {
                oldUser.setUserName(user.getUserName());
            }

            // Update and encode the password if provided
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            // Save the updated user
            userService.saveEntry(oldUser);

            // Return success response
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }

        // If the user is not found, return no content
        return new ResponseEntity<>("User not found", HttpStatus.NO_CONTENT);
    }

}
