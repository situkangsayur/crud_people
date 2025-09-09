package com.jejakin.plugin.host;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jejakin.crud.people.config.PeopleCrudPluginConfig;
import com.jejakin.crud.people.model.Person;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PluginHostApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long personId;

    @Test
    @Order(1)
    void createPerson() throws Exception {
        Person newPerson = new Person("1234567890123456", "John", "Doe", "08123456789", "Jl. Contoh No. 1", "Jakarta");

        String responseString = mockMvc.perform(post("/api/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andReturn().getResponse().getContentAsString();

        Person createdPerson = objectMapper.readValue(responseString, Person.class);
        personId = createdPerson.getId();
    }

    @Test
    @Order(2)
    void getAllPeople() throws Exception {
        mockMvc.perform(get("/api/people")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].firstName").value("John"));
    }

    @Test
    @Order(3)
    void getPersonById() throws Exception {
        mockMvc.perform(get("/api/people/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(personId));
    }

    @Test
    @Order(4)
    void updatePerson() throws Exception {
        Person updatedPerson = new Person("1234567890123456", "Jane", "Doe", "08123456789", "Jl. Contoh No. 1", "Surabaya");
        updatedPerson.setId(personId);

        mockMvc.perform(put("/api/people/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPerson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.firstName").value("Jane"))
                .andExpect(jsonPath("$.data.address2").value("Surabaya"));
    }

    @Test
    @Order(5)
    void deletePerson() throws Exception {
        mockMvc.perform(delete("/api/people/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        mockMvc.perform(get("/api/people/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}