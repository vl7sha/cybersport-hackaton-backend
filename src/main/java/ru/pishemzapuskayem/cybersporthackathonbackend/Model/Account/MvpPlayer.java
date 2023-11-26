package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.AbstractEntity;

@Getter
@Setter
@Entity
public class MvpPlayer extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;

    private String cause;
}
