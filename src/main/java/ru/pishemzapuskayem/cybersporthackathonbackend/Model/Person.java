package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Person extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String middleName;
    private String subjectOfRF;
    private LocalDate birthDate;

    @ElementCollection
    private List<String> contacts;

    public Person(List<String> contacts) {
        this.contacts = contacts;
    }
}
