package com.educonnect.journalApp.service;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.repository.UserRespository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean saveNewEntry(User user) {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setRoles(Arrays.asList("USER"));
            user.setPassword(encodedPassword);
            userRespository.save(user);

            return true;
        } catch (Exception e) {
            logger.info("Hahahah");
            e.printStackTrace();
            return false;
        }

    }

    public void saveAdmin(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        user.setPassword(encodedPassword);
        userRespository.save(user);
    }

    public void saveUser(User user) {
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
