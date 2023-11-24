package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.PlayerDTO;

import java.util.List;

@Getter
@Setter
public class TeamDTO {
    private Long id;
    private String name;
    private PlayerDTO captain;
    private List<PlayerDTO> players;
}
