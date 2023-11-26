package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Judge.JudgeDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.Stages.CompletedMatchDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.Stages.CompletedStageDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Judge;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Match;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentResult;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentStage;

import java.util.List;
import java.util.stream.Collectors;

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

    public Tournament mapCompletedTournament(CompletedTournamentDTO completedTournamentDTO) {
        Tournament tournament = modelMapper.map(completedTournamentDTO, Tournament.class);
        tournament.setIsStarted(true);

        for (CompletedStageDTO stageDTO : completedTournamentDTO.getStages()) {
            TournamentStage stage = stageMapper.map(stageDTO);
            stage.setTournament(tournament);

            for (CompletedMatchDTO matchDTO : stageDTO.getMatches()) {
                Match match = new Match();
                match.setTeam1(teamMapper.map(matchDTO.getTeam1Id()));
                match.setTeam2(teamMapper.map(matchDTO.getTeam2Id()));
                match.setWinnerTeam(teamMapper.map(matchDTO.getWinnerTeamId()));
                match.setTournamentStage(stage);
                stage.getMatches().add(match);
            }

            tournament.getStages().add(stage);
        }

        if (completedTournamentDTO.getChiefJudgeId() != null) {
            Judge chiefJudge = judgeMapper.map(completedTournamentDTO.getChiefJudgeId());
            tournament.setChiefJudge(chiefJudge);
        }

        if (completedTournamentDTO.getChiefSecretaryId() != null) {
            Judge chiefSecretary = judgeMapper.map(completedTournamentDTO.getChiefSecretaryId());
            tournament.setChiefSecretary(chiefSecretary);
        }

        if (completedTournamentDTO.getJudgeIds() != null) {
            List<Judge> judges = completedTournamentDTO.getJudgeIds().stream()
                    .map(judgeMapper::map)
                    .collect(Collectors.toList());
            tournament.setJudges(judges);
        }

        if (completedTournamentDTO.getSecretaryIds() != null) {
            List<Judge> secretaries = completedTournamentDTO.getSecretaryIds().stream()
                    .map(judgeMapper::map)
                    .collect(Collectors.toList());
            tournament.setSecretaries(secretaries);
        }

        if (completedTournamentDTO.getResults() != null) {
            List<TournamentResult> results = completedTournamentDTO.getResults().stream()
                    .map(this::mapResultDTOToTournamentResult)
                    .collect(Collectors.toList());
            results.forEach(result -> result.setTournament(tournament));
            tournament.setResults(results);
        }


        return tournament;
    }

    private TournamentResult mapResultDTOToTournamentResult(CompletedTournamentResultDTO resultDTO) {
        TournamentResult result = new TournamentResult();
        result.setTeam(teamMapper.map(resultDTO.getTeamId()));
        result.setAllStagesScore(resultDTO.getAllStagesScore());
        result.setTakenPlace(resultDTO.getTakenPlace());
        return result;
    }
}
