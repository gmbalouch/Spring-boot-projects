package com.educonnect.journalApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.journalApp.apiResponse.WeatherResponse;
import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.service.UserService;
import com.educonnect.journalApp.service.WeatherService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
@Slf4j
public class userController2 {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WeatherService weatherService;

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
            userService.saveNewEntry(oldUser);

            // Return success response
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }

        // If the user is not found, return no content
        return new ResponseEntity<>("User not found", HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Islamabad,Pakistan");

        String greeting = "";
        if (weatherResponse != null) {
            greeting = " weather feels like " + weatherResponse.getCurrent().feelslike;
        }

        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }

}
