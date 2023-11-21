package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Team extends AbstractEntity {
    private String name;
    private String invitationToken;

    @OneToOne
    private Player captain;

    @OneToMany
    private List<Player> players;
}
