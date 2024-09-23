package com.educonnect.journalApp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import com.mongodb.lang.NonNull;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "users")
@Data

public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull

    private String userName;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();

}
