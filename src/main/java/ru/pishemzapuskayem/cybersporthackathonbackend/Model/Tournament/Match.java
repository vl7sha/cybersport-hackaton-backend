package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.AbstractEntity;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

@Getter
@Setter
@Entity
public class Match extends AbstractEntity {

    @ManyToOne
    private Team winnerTeam;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;


    @ManyToOne
    private TournamentStage tournamentStage;
}
