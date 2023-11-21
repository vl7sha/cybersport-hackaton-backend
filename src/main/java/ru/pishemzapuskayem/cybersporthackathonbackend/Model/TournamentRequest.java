package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TournamentRequest extends AbstractEntity  {
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private Boolean isApproved;
}
