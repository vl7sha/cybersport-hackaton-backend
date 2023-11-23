package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.TournamentRequest.TournamentRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

@Getter
@Setter
public class TournamentResultDTO {
    private TournamentRequestDTO tournament;

    private Integer takenPlace;
}
