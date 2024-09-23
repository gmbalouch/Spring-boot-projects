package com.educonnect.journalApp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Getter
@Setter

public class JournalEntry {
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;

}
