package com.educonnect.journalApp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import com.mongodb.lang.NonNull;

import lombok.Builder;
import lombok.Data;
import java.util.*;

@Document(collection = "User")
@Data
@Builder

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

    private List<String> roles;

}
