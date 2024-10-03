package com.educonnect.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.repository.UserRespository;

public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRespository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRespository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (user != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName()).password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0])).build();

        }

        throw new UsernameNotFoundException("User not found username : " + username);
    }

}
