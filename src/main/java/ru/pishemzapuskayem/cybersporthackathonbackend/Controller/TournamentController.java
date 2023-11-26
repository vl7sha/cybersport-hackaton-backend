package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Matches.MatchDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Pagination.PageDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.MatchMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.TournamentMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.SearchCriteria.XPage;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.TournamentRequestService;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.TournamentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Tournament")
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentMapper tournamentMapper;
    private final MatchMapper matchMapper;
    private final TournamentRequestService tournamentRequestService;

    @GetMapping("/{tournamentId}")
    public ResponseEntity<TournamentDTO> getTournament(@PathVariable Long tournamentId) {
        Tournament tournament = tournamentService.findById(tournamentId);
        return ResponseEntity.ok(tournamentMapper.mapLong(tournament));
    }

    @GetMapping("/{tournamentId}/current-stage/matches")
    public ResponseEntity<PageDTO<MatchDTO>> getCurrentStageMatches(@PathVariable Long tournamentId, XPage page) {
        Page<MatchDTO> dtos = tournamentService.findCurrentStageMatches(tournamentId, page)
                .map(matchMapper::map);

        return ResponseEntity.ok(
                new PageDTO<>(
                        dtos.getContent(),
                        dtos.getTotalElements()
                )
        );
    }

    @GetMapping
    public ResponseEntity<PageDTO<TournamentShortDTO>> getTournaments(XPage page) {
        Page<TournamentShortDTO> dtos = tournamentService.findAllTournaments(page)
                .map(tournamentMapper::map);

        return ResponseEntity.ok(
                new PageDTO<>(
                        dtos.getContent(),
                        dtos.getTotalElements()
                )
        );
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Void> create(@RequestBody CreateTournamentRequest createTournamentRequest) {
        tournamentService.create(tournamentMapper.map(createTournamentRequest));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Void> addCompletedTournament(@RequestBody CompletedTournamentDTO completedTournamentDTO) {
        Tournament completedTournament = tournamentMapper.mapCompletedTournament(completedTournamentDTO);
        tournamentService.save(completedTournament);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tournamentId}/send-request")
    @PreAuthorize("hasRole('CAPTAIN')")
    public ResponseEntity<Void> sendRequest(@PathVariable Long tournamentId, @RequestParam Long teamId) {
        tournamentRequestService.sendJoinRequest(tournamentId, teamId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tournamentId}/join")
    @PreAuthorize("hasRole('CAPTAIN')")
    public ResponseEntity<Void> tryJoin(@PathVariable Long tournamentId, @RequestParam Long teamId) {
        tournamentRequestService.tryJoin(tournamentId, teamId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tournamentId}/judges")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> addJudge(@PathVariable Long tournamentId, @RequestParam Long judgeId) {
        tournamentService.addJudge(tournamentId, judgeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tournamentId}/judges/{judgeId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJudge(@PathVariable Long tournamentId, @PathVariable Long judgeId) {
        tournamentService.removeJudge(tournamentId, judgeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tournamentId}/secretary/{secretaryId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> addSecretary(@PathVariable Long tournamentId, @PathVariable Long secretaryId) {
        tournamentService.addSecretary(tournamentId, secretaryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tournamentId}/secretary/{secretaryId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSecretary(@PathVariable Long tournamentId, @PathVariable Long secretaryId) {
        tournamentService.removeSecretary(tournamentId, secretaryId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{tournamentId}/chief/secretary/{secretariesId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateChiefSecretary(@PathVariable Long tournamentId, @PathVariable Long secretariesId) {
        tournamentService.updateChiefSecretary(tournamentId, secretariesId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tournamentId}/chief/secretary/{secretariesId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> addChiefSecretary(@PathVariable Long tournamentId, @PathVariable Long secretariesId) {
        tournamentService.addChiefSecretary(tournamentId, secretariesId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{tournamentId}/chief/judges/{judgeId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateChiefJudge(@PathVariable Long tournamentId, @PathVariable Long judgeId) {
        tournamentService.updateChiefJudge(tournamentId, judgeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tournamentId}/start")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> startTournament(@PathVariable Long tournamentId) {
        tournamentService.startTournament(tournamentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tournamentId}/matches/{matchId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> determineWinner(@PathVariable Long tournamentId, @PathVariable Long matchId,
                                                @RequestBody EndMatchRequestDTO requestDTO) {
        tournamentService.determineWinner(
                tournamentId,
                matchId,
                requestDTO.getWinnerTeamId(),
                requestDTO.getWinnerScore(),
                requestDTO.getLoserScore()
        );

        return ResponseEntity.ok().build();
    }
}
