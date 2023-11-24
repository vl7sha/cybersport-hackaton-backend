package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Judge.JudgeDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamDTO;

import java.util.List;

@Getter
@Setter
public class TournamentDTO extends TournamentShortDTO {

    private List<TeamDTO> teams;

    private TournamentStageDTO currentStage;

    private List<TournamentStageDTO> stages;

    private List<TournamentResultShortDTO> results;

    private JudgeDTO chiefJudge;

    private JudgeDTO chiefSecretary;

    private List<JudgeDTO> judges;

    private List<JudgeDTO> secretaries;
}
