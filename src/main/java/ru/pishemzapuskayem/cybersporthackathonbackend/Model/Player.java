package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Player extends Person {

    private String gender;
    private String rank;
    private String gto;
    private int numberOfWins;
    private int placeTaken;

    @ManyToOne
    private Team team;
}
