package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.AbstractEntity;

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
    private LocalDate birthDate;
    private String subjectOfRF;

    @ElementCollection
    private List<String> contacts;

    public Person(List<String> contacts) {
        this.contacts = contacts;
    }
}
