package com.jejakin.crud.people.model;

import com.jejakin.crud.abstraction.model.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Document(collection = "people")
public class Person extends BaseEntity {
    private String nik; // Nomor Induk Kependudukan (ID Card Number)
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address1;
    private String address2;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Person(String nik, String firstName, String lastName, String phoneNumber, String address1, String address2) {
        super(null); // Call BaseEntity constructor with null for ID, as it's generated
        this.nik = nik;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address1 = address1;
        this.address2 = address2;
    }

    public Person(Long id, String nik, String firstName, String lastName, String phoneNumber, String address1, String address2) {
        super(id);
        this.nik = nik;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address1 = address1;
        this.address2 = address2;
    }
}