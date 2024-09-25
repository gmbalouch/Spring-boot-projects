package com.educonnect.journalApp.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.repository.UserRespository;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRespository userRespository;

    public void saveEntry(User user) {
        userRespository.save(user);
    }

    public List<User> getEntries() {
        return userRespository.findAll();
    }

    public Optional<User> getById(ObjectId id) {
        return userRespository.findById(id);
    }

    public void deleteEntry(ObjectId id) {
        userRespository.deleteById(id);
    }

    public User findByUser(String userName) {
        User user = userRespository.findByUserName(userName);
        if (user == null) {
            System.out.println("User " + userName + " not found.");
        } else {
            System.out.println("User found: " + user.getUserName());
        }
        return user;
    }

}
