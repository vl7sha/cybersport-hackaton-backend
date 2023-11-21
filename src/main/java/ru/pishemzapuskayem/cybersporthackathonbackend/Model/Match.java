package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Match extends AbstractEntity {

    @ManyToMany
    private List<Team> teams;

    @ManyToOne
    private Team winnerTeam;

    @ManyToOne
    private Tournament tournament;
}
