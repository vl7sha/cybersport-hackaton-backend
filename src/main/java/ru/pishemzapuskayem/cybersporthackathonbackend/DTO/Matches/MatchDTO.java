package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Matches;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamDTO;

@Getter
@Setter
public class MatchDTO {
    private Long id;
    private TeamDTO team1;
    private TeamDTO team2;
    private TeamDTO winnerTeam;
}
