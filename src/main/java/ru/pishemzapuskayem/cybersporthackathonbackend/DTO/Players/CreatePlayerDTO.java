package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreatePlayerDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private String rank;
    private String gto;
    private String subjectOfRF;
    private LocalDate birthDate;
    private List<String> contacts;
}
