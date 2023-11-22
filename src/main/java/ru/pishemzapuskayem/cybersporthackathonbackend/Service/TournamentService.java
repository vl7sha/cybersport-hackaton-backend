package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.Judge.AddJudgesTournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.Judge.DeleteJudgeTournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.Judge.UpdateChiefJudgeTournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Judge;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.JudgeRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRepository;

import java.util.List;

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

    @Transactional
    public void addJudges(Long tournamentId, AddJudgesTournamentRequest addJudgeTournamentRequest) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (tournament.getChiefJudge() != getAuthenticated()){
            throw new ApiException("Вы не можете добавлять судей");
        }

        List<Judge> judges = tournament.getJudges();

        for (Long idJudge: addJudgeTournamentRequest.getIdJudges()
        ) {
            Judge judge = judgeRepository.findById(idJudge)
                    .orElseThrow(() -> new ApiException("Аккаунт судьи не найден"));

            if (tournament.getJudges().contains(judge)){
                throw new ApiException("Такой судья уже есть");
            }

            judges.add(judge);
        }

        tournamentRepository.save(tournament);
    }

    @Transactional
    public void updateChiefJudge(Long tournamentId, UpdateChiefJudgeTournamentRequest addJudgesTournamentRequest) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        List<Judge> judges = tournament.getJudges();

        Judge oldChiefJudge = getAuthenticated();

        Judge newChiefJudge = judgeRepository.findById(addJudgesTournamentRequest.getIdJudge()).orElseThrow(
                () -> new ApiException("Такого аккаунта нет")
        );

        if (oldChiefJudge != tournament.getChiefJudge()){
            throw new ApiException("Вы не можете изменять главного судью");
        }

        tournament.setChiefJudge(newChiefJudge);

        judges.remove(newChiefJudge);
        judges.add(oldChiefJudge);

        tournamentRepository.save(tournament);
    }


    public void deleteChiefJudge(Long tournamentId, DeleteJudgeTournamentRequest deleteJudgeTournamentRequest) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        List<Judge> judges = tournament.getJudges();

        Judge removeChiefJudge = judgeRepository.findById(deleteJudgeTournamentRequest.getIdJudge()).orElseThrow(
                () -> new ApiException("Такого аккаунта нет")
        );

        if (tournament.getChiefJudge() != getAuthenticated()){
            throw new ApiException("Вы не можете удалять судей");
        }

        judges.remove(removeChiefJudge);


        tournamentRepository.save(tournament);
    }

    private Judge getAuthenticated() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return judgeRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Аккаунт судьи не найден"));
    }


}
