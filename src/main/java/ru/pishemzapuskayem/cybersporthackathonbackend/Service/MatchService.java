package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Match;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentStage;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.MatchRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TeamRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public Match save(Match match) {
        Team team1 = teamRepository.findById(match.getTeam1().getId())
                .orElseThrow(() -> new ApiException("Команда 1 не найдена"));

        match.setTeam1(team1);

        Team team2 = teamRepository.findById(match.getTeam2().getId())
                .orElseThrow(() -> new ApiException("Команда 2 не найдена"));
        match.setTeam2(team2);

        if (match.getWinnerTeam() != null && match.getWinnerTeam().getId() != null) {
            Team winnerTeam = teamRepository.findById(match.getWinnerTeam().getId())
                    .orElseThrow(() -> new ApiException("Победившая команда не найдена"));
            match.setWinnerTeam(winnerTeam);
        }

        match.setTournamentStage(null);

        return matchRepository.save(match);
    }
}
