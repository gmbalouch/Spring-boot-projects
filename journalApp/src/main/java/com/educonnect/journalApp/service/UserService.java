package com.educonnect.journalApp.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.educonnect.journalApp.entity.JournalEntry;
import com.educonnect.journalApp.repository.JournalEntryRespository;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRespository journalrRespository;

    public void saveEntry(JournalEntry journalEntry) {
        journalrRespository.save(journalEntry);
    }

    public List<JournalEntry> getEntries() {
        return journalrRespository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id) {
        return journalrRespository.findById(id);
    }

    public void deleteEntry(ObjectId id) {
        journalrRespository.deleteById(id);
    }
}
