package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.CreatePlayerRequestDTO;

import java.util.List;

@Getter
@Setter
public class CreateTeamRequestDTO {
    private String name;
    private List<CreatePlayerRequestDTO> players;
}
