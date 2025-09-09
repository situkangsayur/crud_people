package com.jejakin.crud.people.repository;

import com.jejakin.crud.people.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.jejakin.crud.people.TestApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
@EnableMongoRepositories(basePackageClasses = PersonRepository.class)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertNotNull(personRepository);
    }

    @Test
    void savePerson() {
        Person person = new Person("1234567890", "John", "Doe", "1234567890", "Address1", "Address2");
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person savedPerson = personRepository.save(person);

        assertNotNull(savedPerson);
        assertEquals("John", savedPerson.getFirstName());
        assertEquals("Doe", savedPerson.getLastName());
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void findById() {
        Person person = new Person(1L, "1234567890", "John", "Doe", "1234567890", "Address1", "Address2");
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        Optional<Person> foundPerson = personRepository.findById(1L);

        assertTrue(foundPerson.isPresent());
        assertEquals("John", foundPerson.get().getFirstName());
        assertEquals("Doe", foundPerson.get().getLastName());
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void findAll() {
        Person person1 = new Person(1L, "1111111111", "John", "Doe", "1111111111", "Address1", "Address2");
        Person person2 = new Person(2L, "2222222222", "Jane", "Smith", "2222222222", "Address3", "Address4");
        List<Person> people = Arrays.asList(person1, person2);
        when(personRepository.findAll()).thenReturn(people);

        List<Person> foundPeople = personRepository.findAll();

        assertNotNull(foundPeople);
        assertEquals(2, foundPeople.size());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void deleteById() {
        doNothing().when(personRepository).deleteById(1L);

        personRepository.deleteById(1L);

        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    void existsById() {
        when(personRepository.existsById(1L)).thenReturn(true);

        boolean exists = personRepository.existsById(1L);

        assertTrue(exists);
        verify(personRepository, times(1)).existsById(1L);
    }
}