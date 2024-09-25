package com.educonnect.journalApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
@RequestMapping("/user")
public class userController2 {

    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getAll() {
        return userService.getEntries();
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.saveEntry(user);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
        User oldUser = userService.findByUser(userName);
        if (oldUser != null) {
            oldUser.setUserName(user.getUserName());
            oldUser.setPassword(user.getPassword());
            userService.saveEntry(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
