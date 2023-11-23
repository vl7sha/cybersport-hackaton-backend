package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.AbstractEntity;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

@Getter
@Setter
@Entity
public class TournamentResult extends AbstractEntity {
    //todo результат наверное связан должен быть не с турниром а с этапом турнира хз
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    //нужен для подсчёта занятого места внутри этапа (если команда не прошла дальше) это кишки
    private Integer score;

    private Integer takenPlace;
}
