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

@Document(collection = "config_journal_app")
@Data
@Builder

public class ConfigJournalAppEntity {

    private String key;
    private String value;

}
