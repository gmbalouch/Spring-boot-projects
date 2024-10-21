package com.educonnect.journalApp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.educonnect.journalApp.enums.Sentiment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")

@Data
@NoArgsConstructor
public class JournalEntry {
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

}
