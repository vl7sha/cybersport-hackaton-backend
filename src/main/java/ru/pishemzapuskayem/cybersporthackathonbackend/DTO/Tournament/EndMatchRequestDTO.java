package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EndMatchRequestDTO {
    private Long tournamentId;
    private Long matchId;
    private Long winnerTeamId;
    private Integer winnerScore;
    private Integer loserScore;
}
