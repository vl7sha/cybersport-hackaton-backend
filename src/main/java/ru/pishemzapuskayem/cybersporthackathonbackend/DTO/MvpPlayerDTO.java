package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.PlayerDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamDTO;

@Getter
@Setter
public class MvpPlayerDTO {

    private PlayerDTO player;

    private String cause;
}
