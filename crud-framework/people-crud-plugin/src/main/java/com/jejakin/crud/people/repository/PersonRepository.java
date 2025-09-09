package com.jejakin.crud.people.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.jejakin.crud.people.model.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends MongoRepository<Person, Long> {
    // Custom repository methods for Person can be added here if needed
}