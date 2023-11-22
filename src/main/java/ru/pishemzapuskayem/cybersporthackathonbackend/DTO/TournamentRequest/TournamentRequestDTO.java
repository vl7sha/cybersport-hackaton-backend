package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.TournamentRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.TournamentShortDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TournamentRequestDTO {
    private TournamentShortDTO tournament;
    private TeamDTO team;
    private Boolean isApproved;
}
