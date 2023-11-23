package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.TournamentRequest.TournamentRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

@Getter
@Setter
public class TournamentResultDTO {

    private TournamentRequestDTO tournament;

    //нужен для подсчёта занятого места внутри этапа (если команда не прошла дальше) это кишки
    private Integer score;

    private Integer takenPlace;
}
