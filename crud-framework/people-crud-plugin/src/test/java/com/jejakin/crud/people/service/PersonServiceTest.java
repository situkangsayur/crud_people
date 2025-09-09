package com.jejakin.crud.people.service;

import com.jejakin.crud.people.model.Person;
import com.jejakin.crud.people.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void savePerson() {
        Person person = new Person("1234567890", "John", "Doe", "1234567890", "Address1", "Address2");
        when(repository.save(person)).thenReturn(person);

        Person savedPerson = personService.save(person);

        assertNotNull(savedPerson);
        assertEquals("John", savedPerson.getFirstName());
        assertEquals("Doe", savedPerson.getLastName());
        verify(repository, times(1)).save(person);
    }

    @Test
    void findPersonById() {
        Person person = new Person(1L, "1234567890", "John", "Doe", "1234567890", "Address1", "Address2");
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        Optional<Person> foundPerson = personService.findById(1L);

        assertTrue(foundPerson.isPresent());
        assertEquals("John", foundPerson.get().getFirstName());
        assertEquals("Doe", foundPerson.get().getLastName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findAllPeople() {
        Person person1 = new Person(1L, "1111111111", "John", "Doe", "1111111111", "Address1", "Address2");
        Person person2 = new Person(2L, "2222222222", "Jane", "Smith", "2222222222", "Address3", "Address4");
        List<Person> people = Arrays.asList(person1, person2);
        when(repository.findAll()).thenReturn(people);

        List<Person> foundPeople = personService.findAll();

        assertNotNull(foundPeople);
        assertEquals(2, foundPeople.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deletePersonById() {
        doNothing().when(repository).deleteById(1L);

        personService.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void updatePerson_success() {
        Person existingPerson = new Person(1L, "1234567890", "John", "Doe", "1234567890", "Address1", "Address2");
        Person updatedPerson = new Person("0987654321", "Jonathan", "Doe", "0987654321", "AddressA", "AddressB");
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(Person.class))).thenReturn(updatedPerson);

        Person result = personService.update(1L, updatedPerson);

        assertNotNull(result);
        assertEquals("Jonathan", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(1L, result.getId());
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).save(any(Person.class));
    }

    @Test
    void updatePerson_notFound() {
        Person updatedPerson = new Person("0987654321", "Jonathan", "Doe", "0987654321", "AddressA", "AddressB");
        when(repository.existsById(1L)).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            personService.update(1L, updatedPerson);
        });

        assertEquals("Person with id 1 not found for update.", thrown.getMessage());
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(0)).save(any(Person.class));
    }
}