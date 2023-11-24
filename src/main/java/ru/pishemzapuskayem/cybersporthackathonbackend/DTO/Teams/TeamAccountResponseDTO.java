package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.PlayerDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.TournamentResultShortDTO;

import java.util.List;

@Getter
@Setter
public class TeamAccountResponseDTO {

    private String name;

    private PlayerDTO captain;

    private List<PlayerDTO> players;

    private List<TournamentResultShortDTO> results;

}
