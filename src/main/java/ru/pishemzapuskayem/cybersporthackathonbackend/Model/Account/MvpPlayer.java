package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.AbstractEntity;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;

@Getter
@Setter
@Entity
public class MvpPlayer extends AbstractEntity {

    @OneToOne
    private Tournament tournament;

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private String cause;
}
