package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Match;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentStage;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentStageRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentStageService {

    private final TournamentStageRepository repository;

    @Transactional
    public List<TournamentStage> createEmptyTournamentStages() {
        List<TournamentStage> stages = new ArrayList<>();

        TournamentStage roundOf16 = new TournamentStage("1/8 финала", 0);
        stages.add(roundOf16);

        TournamentStage quarterfinals = new TournamentStage("1/4 финала", 1);
        stages.add(quarterfinals);

        TournamentStage semifinals = new TournamentStage("Полуфинал", 2);
        stages.add(semifinals);

        TournamentStage finalStage = new TournamentStage("Финал", 3);
        stages.add(finalStage);

        repository.saveAll(stages);

        return stages;
    }

    @Transactional
    public void createMatchesForStage(List<Team> teams, TournamentStage stage) {
        stage.setTeams(teams);

        List<Team> shuffledTeams = new ArrayList<>(teams);
        Collections.shuffle(shuffledTeams);

        if (shuffledTeams.size() % 2 != 0) {
            Match byeMatch = createByeMatch(shuffledTeams.get(shuffledTeams.size() - 1), stage);
            stage.getMatches().add(byeMatch);
            shuffledTeams.remove(shuffledTeams.size() - 1);
        }

        for (int i = 0; i < shuffledTeams.size(); i += 2) {
            Match match = createMatch(shuffledTeams.get(i), shuffledTeams.get(i + 1), stage);
            stage.getMatches().add(match);
        }

        repository.save(stage);
    }

    private Match createByeMatch(Team luckyBastards, TournamentStage stage) {
        Match byeMatch = new Match();
        byeMatch.setTeam1(luckyBastards);
        byeMatch.setTeam2(null);
        byeMatch.setWinnerTeam(luckyBastards);
        byeMatch.setTournamentStage(stage);
        return byeMatch;
    }

    private Match createMatch(Team team1, Team team2, TournamentStage stage) {
        Match match = new Match();
        match.setTeam1(team1);
        match.setTeam2(team2);
        match.setTournamentStage(stage);
        return match;
    }
}
