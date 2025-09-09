package com.jejakin.crud.people.controller;

import com.jejakin.crud.people.model.Person;
import com.jejakin.crud.people.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPerson_shouldReturnCreatedPerson() {
        Person personToSave = new Person("1234567890", "John", "Doe", "1234567890", "Address1", "Address2");
        Person savedPerson = new Person(1L, "1234567890", "John", "Doe", "1234567890", "Address1", "Address2");
        when(personService.save(personToSave)).thenReturn(savedPerson);

        ResponseEntity<Person> response = personController.createPerson(personToSave);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("John", response.getBody().getFirstName());
        verify(personService, times(1)).save(personToSave);
    }

    @Test
    void getPersonById_shouldReturnPerson_whenFound() {
        Person person = new Person(1L, "1234567890", "John", "Doe", "1234567890", "Address1", "Address2");
        when(personService.findById(1L)).thenReturn(Optional.of(person));

        ResponseEntity<Person> response = personController.getPersonById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(personService, times(1)).findById(1L);
    }

    @Test
    void getPersonById_shouldReturnNotFound_whenNotFound() {
        when(personService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Person> response = personController.getPersonById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(personService, times(1)).findById(1L);
    }

    @Test
    void getAllPeople_shouldReturnListOfPeople() {
        Person person1 = new Person(1L, "1111111111", "John", "Doe", "1111111111", "Address1", "Address2");
        Person person2 = new Person(2L, "2222222222", "Jane", "Smith", "2222222222", "Address3", "Address4");
        List<Person> people = Arrays.asList(person1, person2);
        when(personService.findAll()).thenReturn(people);

        ResponseEntity<List<Person>> response = personController.getAllPeople();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(personService, times(1)).findAll();
    }

    @Test
    void updatePerson_shouldReturnUpdatedPerson_whenFound() {
        Person updatedPersonDetails = new Person("0987654321", "Jonathan", "Doe", "0987654321", "AddressA", "AddressB");
        Person existingPerson = new Person(1L, "1234567890", "John", "Doe", "1234567890", "Address1", "Address2");
        Person returnedUpdatedPerson = new Person(1L, "0987654321", "Jonathan", "Doe", "0987654321", "AddressA", "AddressB");

        when(personService.update(1L, updatedPersonDetails)).thenReturn(returnedUpdatedPerson);

        ResponseEntity<Person> response = personController.updatePerson(1L, updatedPersonDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Jonathan", response.getBody().getFirstName());
        verify(personService, times(1)).update(1L, updatedPersonDetails);
    }

    @Test
    void updatePerson_shouldReturnNotFound_whenNotFound() {
        Person updatedPersonDetails = new Person("0987654321", "Jonathan", "Doe", "0987654321", "AddressA", "AddressB");
        when(personService.update(1L, updatedPersonDetails)).thenThrow(new RuntimeException("Person not found"));

        ResponseEntity<Person> response = personController.updatePerson(1L, updatedPersonDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(personService, times(1)).update(1L, updatedPersonDetails);
    }

    @Test
    void deletePerson_shouldReturnNoContent() {
        doNothing().when(personService).deleteById(1L);

        ResponseEntity<Void> response = personController.deletePerson(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(personService, times(1)).deleteById(1L);
    }
}