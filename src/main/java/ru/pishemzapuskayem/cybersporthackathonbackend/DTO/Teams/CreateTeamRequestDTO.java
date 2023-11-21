package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreatePlayerRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.CreatePlayerDTO;

import java.util.List;

@Getter
@Setter
public class CreateTeamRequestDTO {
    private String name;
    private List<CreatePlayerRequest> players;
}
