package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentResult;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TeamRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentResultRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentResultService {
    private final TournamentResultRepository resultRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void saveOrUpdateStageResult(Tournament tournament, Team team, int score) {
        TournamentResult result = resultRepository
                .findByTournamentAndTeam(tournament, team)
                .orElse(null);

        if (result != null) {
            result.setScore(score);
            result.setAllStagesScore(result.getAllStagesScore() + score);
        } else {
            result = new TournamentResult();
            result.setTournament(tournament);
            result.setTeam(team);
            result.setScore(score);
            result.setAllStagesScore(score);
            result.setTakenPlace(null);

            if (tournament.getResults() == null) {
                tournament.setResults(new ArrayList<>());
            }

            tournament.getResults().add(result);
        }

        resultRepository.save(result);
    }

    @Transactional
    public void setTakenPlaces(List<Team> teamsNotInNextStage, Tournament tournament) {
        Map<Team, TournamentResult> resultsMap = tournament.getResults().stream()
                .collect(Collectors.toMap(TournamentResult::getTeam, Function.identity()));

        List<Team> sortedTeams = sortByRating(teamsNotInNextStage, resultsMap);

        int lastTakenPlace = tournament.getLastTakenPlace() == null
                ? tournament.getTeams().size() + 1 : tournament.getLastTakenPlace();

        for (int i = 0; i < sortedTeams.size(); i++) {
            if (i == 0) {
                lastTakenPlace = lastTakenPlace - (i + 1);
            } else {
                lastTakenPlace = lastTakenPlace - i;
            }

            TournamentResult result = resultsMap.get(sortedTeams.get(i));
            result.setTakenPlace(lastTakenPlace);
        }

        tournament.setLastTakenPlace(lastTakenPlace);
    }

    @Transactional
    public TournamentResult save(TournamentResult result) {
        if (result.getTeam() != null && result.getTeam().getId() != null) {
            Team team = teamRepository.findById(result.getTeam().getId())
                    .orElseThrow(() -> new ApiException("Команда не найдена"));
            result.setTeam(team);
        }

        return resultRepository.save(result);
    }

    private List<Team> sortByRating(List<Team> teams, Map<Team, TournamentResult> resultsMap) {
        return teams.stream()
                .sorted(Comparator.comparingInt(team -> resultsMap.get(team).getScore()))
                .collect(Collectors.toList());
    }
}
