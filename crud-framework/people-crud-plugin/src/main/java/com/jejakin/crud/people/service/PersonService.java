package com.jejakin.crud.people.service;

import com.jejakin.crud.people.model.Person;
import com.jejakin.crud.people.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public Optional<Person> findById(Long id) {
        return repository.findById(id);
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Person update(Long id, Person person) {
        if (repository.existsById(id)) {
            person.setId(id); // Ensure the ID is set for update
            return repository.save(person);
        }
        throw new RuntimeException("Person with id " + id + " not found for update.");
    }
}