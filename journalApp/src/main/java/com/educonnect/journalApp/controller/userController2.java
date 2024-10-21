package com.educonnect.journalApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.journalApp.apiResponse.WeatherResponse;
import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.repository.UserRepositoryImpl;
import com.educonnect.journalApp.service.EmailService;
import com.educonnect.journalApp.service.UserService;
import com.educonnect.journalApp.service.WeatherService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user")
@Slf4j
public class userController2 {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

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

    @GetMapping("/SAUsers")
    public ResponseEntity<?> getSAUsers() {

        List<User> users = userRepositoryImpl.getUserForSA();

        StringBuilder SAUsers = new StringBuilder();

        for (User user : users) {
            SAUsers.append(user.getUserName() + "\n");
        }

        return new ResponseEntity<>("Users for Sentimental Analyses are  \n" + SAUsers.toString(), HttpStatus.OK);
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody Map<String, String> emailDetails) {
        String to = emailDetails.get("to");
        String subject = emailDetails.get("subject");
        String body = emailDetails.get("body");

        log.info("To " + to);
        if (to == null || subject == null || body == null) {
            return new ResponseEntity<>("Email details are incomplete", HttpStatus.BAD_REQUEST);
        }

        try {
            Boolean send = emailService.sendEmail(to, subject, body);
            return new ResponseEntity<>("Email sent successfully " + send, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error sending email: ", e);
            return new ResponseEntity<>("Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
