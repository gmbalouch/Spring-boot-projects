package com.educonnect.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service; // Make sure to add this annotation for your service

import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.repository.UserRespository;

@Service // Ensure this class is recognized as a Spring service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRespository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Ensure roles are properly initialized
        String[] roles = user.getRoles() != null ? user.getRoles().toArray(new String[0]) : new String[0];

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .roles(roles) // This must not be null
                .build();
    }
}
