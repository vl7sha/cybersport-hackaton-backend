package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;

import java.time.LocalDate;
import java.util.List;

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
    private Account organizer;
    private Account chiefJudge;
    private Account chiefSecretary;
    private List<Account> judges;
    private List<Account> secretaries;
}
