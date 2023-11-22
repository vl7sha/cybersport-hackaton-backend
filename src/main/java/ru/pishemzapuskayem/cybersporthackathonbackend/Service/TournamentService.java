package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Judge;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.JudgeRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final JudgeRepository judgeRepository;

    @Transactional
    public void create(Tournament tournament){
        Judge org = getAuthenticated();
        tournament.setChiefJudge(org);
        tournamentRepository.save(tournament);
    }

    private Judge getAuthenticated() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return judgeRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Аккаунт игрока не найден"));
    }
}
