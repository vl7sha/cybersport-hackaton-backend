package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreateAccountRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private List<String> contacts;
    private LocalDate birthDate;
    private String subjectOfRF;

    private String email;
    private String password;
}
