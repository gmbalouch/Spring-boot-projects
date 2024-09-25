package com.educonnect.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.educonnect.journalApp.entity.User;

public interface UserRespository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);
}
