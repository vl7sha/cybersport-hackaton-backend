package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Judge;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Match;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentStage;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.JudgeRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.SearchCriteria.XPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final JudgeRepository judgeRepository;
    private final TournamentStageService tournamentStageService;
    private final TournamentResultService tournamentResultService;

    public Page<Tournament> findAllTournaments(XPage page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getItemsPerPage());
        return tournamentRepository.findAll(pageable);
    }

    @Transactional
    public void create(Tournament tournament){
        Judge org = getAuthenticated();
        tournament.setChiefJudge(org);
        tournament.setIsStarted(false);

        //todo если участников мало то этапов может быть меньше
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
        TournamentStage firstStage = findFirstStage(tournament);
        tournament.setCurrentStage(firstStage);

        tournamentStageService.createMatchesForStage(tournament.getTeams(), firstStage);
        tournamentRepository.save(tournament);
    }

    @Transactional
    public void determineWinner(Long tournamentId, Long matchId, Long winnerTeamId,
                                Integer winnerScore, Integer loserScore
    ) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (getAuthenticated() != tournament.getChiefJudge()){
            throw new ApiException("Действие разрешено только главному судье");
        }

        Match match = tournament.getStages()
                .stream()
                .flatMap(stage -> stage.getMatches().stream())
                .filter(m -> m.getId().equals(matchId))
                .findFirst()
                .orElseThrow(() -> new ApiException("Матч не найден"));

        Team winnerTeam = tournament.getTeams()
                .stream()
                .filter(team -> team.getId().equals(winnerTeamId))
                .findFirst()
                .orElseThrow(() -> new ApiException("Команда не найдена"));

        Team loserTeam = Objects.equals(match.getTeam1(), winnerTeam)
                ? match.getTeam2() : match.getTeam1();

        match.setWinnerTeam(winnerTeam);
        tournamentResultService.saveOrUpdateStageResult(tournament, winnerTeam, winnerScore);
        tournamentResultService.saveOrUpdateStageResult(tournament, loserTeam, loserScore);

        boolean hasNextStage = tournament.getStages().stream()
                .anyMatch(stage -> stage.getStage() > tournament.getCurrentStage().getStage());

        //todo может не сразу переходить на следующий этап а делать это по запросу
        if (hasNextStage) {
            // переход на следующий этап
            TournamentStage nextStage = findNextStage(tournament);
            nextStage.getTeams().add(winnerTeam);

            if (nextStage.getMatches().stream().allMatch(m -> m.getWinnerTeam() != null)) {
                List<Team> teamsNotInNextStage = new ArrayList<>(tournament.getCurrentStage().getTeams());
                teamsNotInNextStage.removeAll(nextStage.getTeams());
                tournamentResultService.setTakenPlaces(teamsNotInNextStage, tournament);

                tournamentStageService.createMatchesForStage(nextStage.getTeams(), nextStage);
                tournament.setCurrentStage(nextStage);
            }
        } else {
            // закончить турнир
            tournamentResultService.setTakenPlaces(
                    List.of(winnerTeam, loserTeam),
                    tournament
            );
        }

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

    private TournamentStage findFirstStage(Tournament tournament) {
        return tournament.getStages()
                .stream()
                .filter(stage -> stage.getStage() == 0)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private TournamentStage findNextStage(Tournament tournament) {
        Integer nextStageNumber = tournament.getCurrentStage().getStage() + 1;
        return tournament.getStages()
                .stream()
                .filter(stage -> stage.getStage().equals(nextStageNumber))
                .findFirst()
                .orElseThrow(() -> new ApiException("Следующий этап не найден"));
    }
}
