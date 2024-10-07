package com.educonnect.journalApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.journalApp.entity.JournalEntry;
import com.educonnect.journalApp.entity.User;
import com.educonnect.journalApp.service.JournalEntryService;
import com.educonnect.journalApp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping("/check")
    public String check() {
        return "Working";
    }

    @GetMapping("/getEntities")
    public ResponseEntity<?> getAllEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserName = authentication.getName();

        User user = userService.findByUser(authenticatedUserName);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND); // Handle user not found case
        }

        List<JournalEntry> entries = user.getJournalEntries();
        if (entries != null && !entries.isEmpty()) {
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry jEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        System.out.println("Received request with userName: " + userName);

        try {
            jEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(jEntry, userName);
            return new ResponseEntity<>(jEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Print full error stack trace for debugging
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUser(userName);

        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {

            Optional<JournalEntry> jE = journalEntryService.getById(myId);
            if (jE.isPresent()) {
                return new ResponseEntity<>(jE.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // return journalEntryService.getById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteEntry(myId, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateEntry(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry updatedEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUser(userName);

        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {

            Optional<JournalEntry> jE = journalEntryService.getById(myId);
            if (jE.isPresent()) {
                JournalEntry oldEntry = jE.get();

                oldEntry.setTitle(
                        updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("")
                                ? updatedEntry.getTitle()
                                : oldEntry.getTitle());

                oldEntry.setContent(updatedEntry.getContent() != null &&
                        !updatedEntry.getContent().equals("")
                                ? updatedEntry.getContent()
                                : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);

                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
