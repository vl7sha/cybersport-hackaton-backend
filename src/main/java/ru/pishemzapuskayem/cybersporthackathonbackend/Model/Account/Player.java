package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("2")
public class Player extends Account {

    private String gender;
    private Integer rank;
    private String gto;

    @ManyToOne
    private Team team;

    @OneToOne
    private MvpPlayer mpv;
}
