package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentResultShortDTO {
    private Long id;
    private Long teamId;
    private Integer takenPlace;
}
