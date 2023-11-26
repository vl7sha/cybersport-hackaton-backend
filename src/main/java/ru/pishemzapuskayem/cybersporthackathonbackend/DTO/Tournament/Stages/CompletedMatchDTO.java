package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.Stages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletedMatchDTO {
    private Long team1Id;
    private Long team2Id;
    private Long winnerTeamId;
}
