package com.educonnect.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.educonnect.journalApp.entity.JournalEntry;

public interface JournalEntryRespository extends MongoRepository<JournalEntry, ObjectId> {

}
