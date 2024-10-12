package com.educonnect.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.repository.UserRespository;

public class UserDetailsServiceImpTests {

    @InjectMocks
    private UserDetailsServiceImp userDetailsServiceImp;

    @Mock
    private UserRespository userRespository;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUserNameTest() {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username("ali")
                .password("432423")
                .roles("USER")
                .build();

        when(userRespository.findByUserName(anyString()))
                .thenReturn(User.builder()
                        .userName("ali")
                        .password("432423")
                        .roles(new ArrayList<>()) // Initialize roles
                        .build());

        assertNotNull(userDetails);
    }

}
