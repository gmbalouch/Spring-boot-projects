package com.educonnect.journalApp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.educonnect.journalApp.entity.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA() {
        Query query = new Query();
        // query.addCriteria(Criteria.where("userName").is("Abc"));
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"));
        // query.addCriteria(Criteria.where("email").is(true));
        // query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("sentimentalAnalysis").is(true));

        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }

}
