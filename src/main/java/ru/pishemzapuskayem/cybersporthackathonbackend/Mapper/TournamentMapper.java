package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateTournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Judge.JudgeDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.TournamentDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.TournamentResultShortDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.TournamentShortDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.TournamentStageDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TournamentMapper {

    private final ModelMapper modelMapper;
    private final TeamMapper teamMapper;
    private final TournamentStageMapper stageMapper;
    private final JudgeMapper judgeMapper;
    private final TournamentResultMapper tournamentResultMapper;

    public Tournament map(CreateTournamentRequest createTournamentRequest){
        return modelMapper.map(createTournamentRequest, Tournament.class);
    }

    public TournamentShortDTO map(Tournament tournament) {
        return modelMapper.map(tournament, TournamentShortDTO.class);
    }

    public TournamentDTO mapLong(Tournament tournament) {
        TournamentDTO dto = modelMapper.map(tournament, TournamentDTO.class);

        List<TeamDTO> teams = tournament.getTeams()
                .stream()
                .map(teamMapper::map)
                .toList();
        dto.setTeams(teams);

        if (tournament.getCurrentStage() != null) {
            dto.setCurrentStage(stageMapper.map(tournament.getCurrentStage()));
        }

        List<TournamentStageDTO> stages = tournament.getStages()
                .stream()
                .map(stageMapper::map)
                .toList();
        dto.setStages(stages);

        List<TournamentResultShortDTO> results = tournament.getResults()
                .stream()
                .map(tournamentResultMapper::map)
                .toList();
        dto.setResults(results);

        if (tournament.getChiefJudge() != null) {
            dto.setChiefJudge(judgeMapper.map(tournament.getChiefJudge()));
        }

        List<JudgeDTO> judgeDTOS = tournament.getJudges()
                .stream()
                .map(judgeMapper::map)
                .toList();
        dto.setJudges(judgeDTOS);


        if (tournament.getChiefSecretary() != null) {
            dto.setChiefJudge(judgeMapper.map(tournament.getChiefSecretary()));
        }

        List<JudgeDTO> secretaries = tournament.getSecretaries()
                .stream()
                .map(judgeMapper::map)
                .toList();
        dto.setSecretaries(secretaries);


        return dto;
    }
}
