package com.educonnect.journalApp.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.educonnect.journalApp.entity.JournalEntry;
import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.repository.JournalEntryRespository;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRespository journalrRespository;
    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String userName) {

        User user = userService.findByUser(userName); // confirming/ searching if user exists/ giving user Along with
        // jouranl entry
        if (user != null) {
            System.out.println("USER: " + userName);
            JournalEntry saved = journalrRespository.save(journalEntry);
            user.getJournalEntries().add(saved); // saving jounral entry in user which is saved current, and it is just
                                                 // a
                                                 // list
            userService.saveEntry(user);

        } else {
            System.out.println("USER not found: " + userName);
            throw new RuntimeException();
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalrRespository.save(journalEntry);

    }

    public List<JournalEntry> getEntries() {
        return journalrRespository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id) {
        return journalrRespository.findById(id);
    }

    public void deleteEntry(ObjectId id, String userName) {
        User user = userService.findByUser(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalrRespository.deleteById(id);
    }
}
