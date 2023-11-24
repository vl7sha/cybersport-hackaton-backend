package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Matches.MatchDTO;

import java.util.List;

@Getter
@Setter
public class TournamentStageDTO {
    private Long id;
    private String name;
    private Integer stage;
    private List<MatchDTO> matches;
}
