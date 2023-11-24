package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.AbstractEntity;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TournamentStageTeam extends AbstractEntity {
    @ManyToOne
    private TournamentStage tournamentStage;

    @ManyToOne
    private Team team;
}
