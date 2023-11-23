package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TeamRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRequestRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.SearchCriteria.XPage;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentRequestService {
    //todo получается только 1 заявка может быть на 1 турнир и если отклоняют то больше не попадёшь

    private final TournamentRequestRepository requestRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final MailService mailService;

    @Transactional
    public void sendJoinRequest(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (tournament.getIsStarted()) {
            throw new ApiException("Турнир уже начался");
        }

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

        mailService.sendTournamentRequestNotification(
                tournament.getChiefJudge(),
                tournament,
                team
        );
    }

    @Transactional
    public void approveJoinRequest(Long requestId) {
        TournamentRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ApiException("Запрос не найден"));

        request.setIsApproved(true);
        request.getTournament().getTeams().add(request.getTeam());
        tournamentRepository.save(request.getTournament());
        requestRepository.save(request);

        mailService.sendRequestApprovedNotification(
                request.getTeam().getCaptain(),
                request.getTournament()
        );
    }

    @Transactional
    public void rejectJoinRequest(Long requestId) {
        TournamentRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ApiException("Запрос не найден"));

        request.setIsApproved(false);
        requestRepository.save(request);

        mailService.sendRequestRejectedNotification(
                request.getTeam().getCaptain(),
                request.getTournament()
        );
    }

    public void tryJoin(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        if (tournament.getIsStarted()) {
            throw new ApiException("Турнир уже начался");
        }

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

    public Page<TournamentRequest> findPendingRequestsByTournamentId(XPage page, Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ApiException("Турнир не найден"));

        Pageable pageable = PageRequest.of(page.getPage(), page.getItemsPerPage());
        return requestRepository.findByTournamentAndIsApprovedIsNull(tournament, pageable);
    }
}
