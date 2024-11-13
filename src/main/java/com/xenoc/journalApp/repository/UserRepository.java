package com.xenoc.journalApp.repository;

import com.xenoc.journalApp.entity.JournalEntry;
import com.xenoc.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);
}