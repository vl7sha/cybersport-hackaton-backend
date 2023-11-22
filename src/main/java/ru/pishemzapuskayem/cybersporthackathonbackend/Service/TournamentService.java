package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Judge;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentStage;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.JudgeRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final JudgeRepository judgeRepository;
    private final TournamentStageService tournamentStageService;

    @Transactional
    public void create(Tournament tournament){
        Judge org = getAuthenticated();
        tournament.setChiefJudge(org);
        tournament.setIsStarted(false);

        List<TournamentStage> stages = tournamentStageService.createEmptyTournamentStages();
        stages.forEach(stage -> stage.setTournament(tournament));
        tournament.setStages(stages);

        tournamentRepository.save(tournament);
    }

    @Transactional
    public void addJudge(Long tournamentId, Long judgeId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (tournament.getChiefJudge() != getAuthenticated()){
            throw new ApiException("Вы не можете добавлять судей");
        }

        Judge judge = judgeRepository.findById(judgeId)
                .orElseThrow(() -> new ApiException("Аккаунт судьи не найден"));

        if (tournament.getJudges().contains(judge)){
            throw new ApiException("Такой судья уже есть");
        }

        tournament.getJudges().add(judge);
        tournamentRepository.save(tournament);
    }

    @Transactional
    public void removeJudge(Long tournamentId, Long judgeId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (tournament.getChiefJudge() != getAuthenticated()){
            throw new ApiException("Вы не можете удалять судей");
        }

        Judge judge = judgeRepository.findById(judgeId)
                .orElseThrow(() -> new ApiException("Аккаунт судьи не найден"));

        if (!tournament.getJudges().contains(judge)){
            throw new ApiException("Судья не связан с этим турниром");
        }

        tournament.getJudges().remove(judge);
        tournamentRepository.save(tournament);
    }

    @Transactional
    public void updateChiefJudge(Long tournamentId, Long newJudgeId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        Judge oldChiefJudge = getAuthenticated();
        if (oldChiefJudge != tournament.getChiefJudge()){
            throw new ApiException("Вы не можете изменять главного судью");
        }

        List<Judge> judges = tournament.getJudges();

        Judge newChiefJudge = judgeRepository.findById(newJudgeId).orElseThrow(
                () -> new ApiException("Такого аккаунта нет")
        );

        tournament.setChiefJudge(newChiefJudge);
        judges.remove(newChiefJudge);
        judges.add(oldChiefJudge);

        tournamentRepository.save(tournament);
    }

    @Transactional
    public void startTournament(Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (getAuthenticated() != tournament.getChiefJudge()){
            throw new ApiException("Начать турнир может только главный судья");
        }

        if (tournament.getTeams().isEmpty() || tournament.getTeams().size() < 2) {
            throw new ApiException("Недостаточно участников для начала турнира");
        }

        tournament.setIsStarted(true);

        TournamentStage firstStage = tournament.getStages()
                .stream()
                .filter(stage -> stage.getStage() == 0)
                .findFirst()
                .orElseThrow(IllegalStateException::new);

        tournamentStageService.distributeTeamsInStage(tournament.getTeams(), firstStage);
        tournamentRepository.save(tournament);
    }

    public void addSecretary(Long tournamentId, Long secretariesId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (tournament.getChiefJudge() != getAuthenticated()){
            throw new ApiException("Вы не можете добавлять секретарей");
        }

        Judge judge = judgeRepository.findById(secretariesId)
                .orElseThrow(() -> new ApiException("Аккаунт не найден"));

        if (tournament.getSecretaries().contains(judge)){
            throw new ApiException("Такой секретарь уже есть");
        }

        tournament.getSecretaries().add(judge);
        tournamentRepository.save(tournament);
    }

    public void addChiefSecretary(Long tournamentId, Long secretariesId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (tournament.getChiefJudge() != getAuthenticated()){
            throw new ApiException("Вы не можете добавлять секретарей");
        }

        Judge chiefSecretary = judgeRepository.findById(secretariesId)
                .orElseThrow(() -> new ApiException("Аккаунт не найден"));

        if (tournament.getChiefSecretary() == chiefSecretary){
            throw new ApiException("Такой секретарь уже стоит");
        }

        tournament.setChiefSecretary(chiefSecretary);
        tournamentRepository.save(tournament);
    }

    public void updateChiefSecretary(Long tournamentId, Long secretariesId){
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (tournament.getChiefJudge() != getAuthenticated()){
            throw new ApiException("Вы не можете добавлять секретарей");
        }

        Judge oldChiefSecretary = tournament.getChiefSecretary();

        Judge newChiefSecretary = judgeRepository.findById(secretariesId)
                .orElseThrow(() -> new ApiException("Аккаунт не найден"));

        if (oldChiefSecretary == newChiefSecretary){
            throw new ApiException("Такой секретарь уже стоит");
        }

        tournament.setChiefSecretary(newChiefSecretary);
        tournamentRepository.save(tournament);
    }

    public void removeSecretary(Long tournamentId, Long secretaryId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (tournament.getChiefJudge() != getAuthenticated()){
            throw new ApiException("Вы не можете удалять секретарей");
        }

        Judge secretary = judgeRepository.findById(secretaryId)
                .orElseThrow(() -> new ApiException("Аккаунт не найден"));

        if (!tournament.getSecretaries().contains(secretary)){
            throw new ApiException("Такого секретаря там нет");
        }

        tournament.getSecretaries().remove(secretary);
        tournamentRepository.save(tournament);
    }

    private Judge getAuthenticated() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return judgeRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Аккаунт судьи не найден"));
    }
}
