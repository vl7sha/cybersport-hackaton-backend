package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Judge;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class JudgeDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private List<String> contacts;
    private LocalDate birthDate;
    private String subjectOfRF;
    private String residence;
    private Integer certificationLevel;
}
