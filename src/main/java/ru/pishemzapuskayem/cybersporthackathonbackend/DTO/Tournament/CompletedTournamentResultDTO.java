package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletedTournamentResultDTO {
    private Long teamId;
    private Integer allStagesScore;
    private Integer takenPlace;
}
