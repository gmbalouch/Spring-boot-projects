package com.educonnect.journalApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping
    public String testing() {
        return "Working ";
    }

    // @PutMapping()
    // public String update(@RequestBody User user) {
    // String userName = user.getUserName();
    // return "Available user:" + userName;

    // }

    @PutMapping()

    public ResponseEntity<?> updateUser(@RequestBody User user) {
        System.out.println("update method");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("working step2");
        String authenticatedUserName = authentication.getName();

        System.out.println("-----------------" + authenticatedUserName);

        User oldUser = userService.findByUser(authenticatedUserName);
        if (oldUser != null) {
            oldUser.setUserName(user.getUserName());
            oldUser.setPassword(user.getPassword());
            userService.saveEntry(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
