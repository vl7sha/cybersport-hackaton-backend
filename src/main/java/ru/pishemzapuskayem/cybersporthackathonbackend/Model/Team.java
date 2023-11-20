package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Team extends AbstractEntity {
    private String name;

    @ManyToOne
    private Account captain;

    @OneToMany
    private List<Player> players;
}
