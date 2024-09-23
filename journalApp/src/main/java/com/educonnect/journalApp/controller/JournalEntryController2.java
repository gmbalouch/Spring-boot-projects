package com.educonnect.journalApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educonnect.journalApp.entity.JournalEntry;
import com.educonnect.journalApp.service.JournalEntryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/journal")
public class JournalEntryController2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<JournalEntry> entries = journalEntryService.getEntries();
        if (entries != null && !entries.isEmpty()) {
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry jEntry) {
        try {
            jEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(jEntry);
            return new ResponseEntity<>(jEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> jE = journalEntryService.getById(myId);
        if (jE.isPresent()) {
            return new ResponseEntity<>(jE.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // return journalEntryService.getById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId) {
        journalEntryService.deleteEntry(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId myId,
            @RequestBody JournalEntry updatedEntry) {
        JournalEntry oldEntry = journalEntryService.getById(myId).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle(
                    updatedEntry.getTitle() != null && !updatedEntry.getTitle().equals("")
                            ? updatedEntry.getTitle()
                            : oldEntry.getTitle());

            oldEntry.setContent(updatedEntry.getContent() != null && !updatedEntry.getContent().equals("")
                    ? updatedEntry.getContent()
                    : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
