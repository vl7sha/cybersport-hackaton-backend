package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.TournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TeamRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRequestRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentRequestService {
    //todo получается только 1 заявка может быть на 1 турнир и если отклоняют то хуй сосёшь

    private final TournamentRequestRepository requestRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final MailService mailService;

    @Transactional
    public void sendJoinRequest(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException("Команда не найдена"));

        Optional<TournamentRequest> requestOpt =
                requestRepository.findByTournamentAndTeam(tournament, team);

        if (requestOpt.isPresent()) {
            throw new ApiException("Приглашение уже отправлено");
        }

        TournamentRequest joinRequest = new TournamentRequest();
        joinRequest.setTournament(tournament);
        joinRequest.setTeam(team);
        joinRequest.setIsApproved(null);

        requestRepository.save(joinRequest);

        mailService.joinRequestEmail(tournament.getChiefJudge(), tournament, team);
    }

    @Transactional
    public void approveJoinRequest(Long requestId) {
        TournamentRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ApiException("Запрос не найден"));

        request.setIsApproved(true);
        requestRepository.save(request);

        mailService.captainEmail(request.getTeam().getCaptain(),request.getTournament(), true);
    }

    @Transactional
    public void rejectJoinRequest(Long requestId) {
        TournamentRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ApiException("Запрос не найден"));

        request.setIsApproved(false);
        requestRepository.save(request);

        mailService.captainEmail(request.getTeam().getCaptain(),request.getTournament(), false);
    }

    public void tryJoin(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException("Команда не найдена"));

        Optional<TournamentRequest> requestOpt =
                requestRepository.findByTournamentAndTeam(tournament, team);

        if (requestOpt.isEmpty()) {
            throw new ApiException("Сначала отправьте запрос на участие");
        }

        TournamentRequest request = requestOpt.get();
        if (request.getIsApproved() == null) {
            throw new ApiException("Заявка ещё не рассмотрена");
        }

        if (!request.getIsApproved()) {
            throw new ApiException("Заявка отклонена");
        }

        if (tournament.getTeams() == null) {
            tournament.setTeams(new ArrayList<>());
        }

        tournament.getTeams().add(team);
        tournamentRepository.save(tournament);
    }
}
