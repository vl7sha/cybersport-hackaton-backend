package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToMany
    private List<Team> teams;

    @OneToMany
    private List<Match> matches;

    @ManyToOne
    private Judge chiefJudge;

    @ManyToOne
    private Judge chiefSecretary;

    @ManyToMany
    private List<Judge> judges;

    @ManyToMany
    private List<Judge> secretaries;
}
