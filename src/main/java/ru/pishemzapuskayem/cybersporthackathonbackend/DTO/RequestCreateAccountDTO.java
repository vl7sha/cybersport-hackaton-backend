package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestCreateAccountDTO {

    private String name;

    private String surname;

    private String secondName;

    private String contact;

    private String password;

    private String email;

    private LocalDate datOfBirth;

    private String theSubjectOfTheRF;

    private String city;
}
