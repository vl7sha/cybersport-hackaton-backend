package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Player;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentResult;

import java.util.List;

@Getter
@Setter
@Entity
public class Team extends AbstractEntity {
    private String name;
    private String invitationToken;

    @OneToOne
    private Player captain;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TournamentResult> results;

    @OneToMany
    private List<Player> players;
}
