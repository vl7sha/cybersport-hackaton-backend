package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.AbstractEntity;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Judge;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Tournament extends AbstractEntity {

    private String name;
    private String location;
    private String discipline;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate registrationDate;
    private LocalDate closingDate;
    private LocalDate reportDate;
    private String organizer;
    private Boolean isStarted;

    @ManyToMany
    private List<Team> teams;

    @OneToOne
    private TournamentStage currentStage;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<TournamentStage> stages;

    @ManyToOne
    private Judge chiefJudge;

    @ManyToOne
    private Judge chiefSecretary;

    @ManyToMany
    private List<Judge> judges;

    @ManyToMany
    private List<Judge> secretaries;
}
