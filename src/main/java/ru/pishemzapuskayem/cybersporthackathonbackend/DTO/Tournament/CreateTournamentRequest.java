package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateTournamentRequest {
    private String name;
    private String location;
    private String discipline;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate registrationDate;
    private LocalDate closingDate;
    private LocalDate reportDate;
    private String organizer;
}
