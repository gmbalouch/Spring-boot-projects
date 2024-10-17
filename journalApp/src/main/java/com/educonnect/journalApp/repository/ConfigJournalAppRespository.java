package com.educonnect.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.educonnect.journalApp.entity.ConfigJournalAppEntity;

public interface ConfigJournalAppRespository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
