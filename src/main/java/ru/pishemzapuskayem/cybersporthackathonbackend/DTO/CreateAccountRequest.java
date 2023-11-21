package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Role;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreateAccountRequest {
    private String name;

    private String surname;

    private String secondName;

    private List<String> contacts;

    private String password;

    private String email;

    private LocalDate dateOfBirth;

    private String theSubjectOfTheRF;
}
