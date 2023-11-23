package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.AbstractEntity;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TournamentStage extends AbstractEntity {
    private String name;
    private Integer stage;

    @ManyToOne
    private Tournament tournament;

    @OneToMany(mappedBy = "tournamentStage")
    private List<Match> matches;

    @ManyToMany
    private List<Team> teams;

    public TournamentStage(String name, Integer stage) {
        this.name = name;
        this.stage = stage;
    }
}
