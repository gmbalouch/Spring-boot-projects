package com.educonnect.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.repository.UserRespository;

@SpringBootTest
public class UserServiceTests {

    // @Autowired
    private UserRespository userRespository;

    @Autowired
    private UserService userService;

    // @Disabled

    @Test
    @ParameterizedTest
    // @CsvSource({
    // "muet",
    // "Abc",
    // "Gm",
    // "Gm2"
    // })

    @ArgumentsSource(UsersArgumentsProvider.class)
    public void testSaveNewEntry(User user) {
        // User user = userRespository.findByUserName("Abc");
        // assertNotNull(userRespository.findByUserName(name), "Test for" + name);
        // assertTrue(!user.getJournalEntries().isEmpty());
        // System.out.println("Testing user: " + user.getUserName());
        assertTrue(userService.saveNewEntry(user));
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "3,2,5",
            "6,5,31",
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

}
