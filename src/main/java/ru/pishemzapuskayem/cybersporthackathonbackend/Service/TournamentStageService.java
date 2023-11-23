package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Match;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentStage;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.MatchRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentStageRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.SearchCriteria.XPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentStageService {

    private final TournamentStageRepository repository;
    private final MatchRepository matchRepository;

    public Page<Match> findCurrentStageMatches(TournamentStage currentStage, XPage page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getItemsPerPage());
        return matchRepository.findByTournamentStage(currentStage, pageable);
    }

    @Transactional
    public TournamentStage createTournamentStage(Integer stage) {
        TournamentStage firstStage = new TournamentStage("Этап " + stage, stage);
        return repository.save(firstStage);
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
