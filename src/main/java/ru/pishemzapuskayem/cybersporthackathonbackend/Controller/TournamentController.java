package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateTournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.EndMatchRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.TournamentMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.TournamentRequestService;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.TournamentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Tournament")
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentMapper tournamentMapper;
    private final TournamentRequestService tournamentRequestService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Void> create(@RequestBody CreateTournamentRequest createTournamentRequest) {
        tournamentService.create(tournamentMapper.map(createTournamentRequest));
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

    @PostMapping("/{tournamentId}/judges/{secretaryId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> addSecretary(@PathVariable Long tournamentId, @PathVariable Long secretaryId){
        tournamentService.addSecretary(tournamentId, secretaryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tournamentId}/judges/{secretaryId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSecretary(@PathVariable Long tournamentId, @PathVariable Long secretaryId){
        tournamentService.removeSecretary(tournamentId, secretaryId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{tournamentId}/judges/{secretariesId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateChiefSecretary(@PathVariable Long tournamentId, @PathVariable Long secretariesId){
        tournamentService.updateChiefSecretary(tournamentId, secretariesId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{tournamentId}/judges/{secretariesId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> addChiefSecretary(@PathVariable Long tournamentId, @PathVariable Long secretariesId){
        tournamentService.addChiefSecretary(tournamentId, secretariesId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{tournamentId}/judges/{judgeId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> updateChiefJudge(@PathVariable Long tournamentId, @PathVariable Long judgeId){
        tournamentService.updateChiefJudge(tournamentId, judgeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tournamentId}/start")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> startTournament(@PathVariable Long tournamentId) {
        tournamentService.startTournament(tournamentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tournamentId}/matches/{matchId}/winner/{teamId}")
    @PreAuthorize("hasRole('JUDGE') or hasRole('ADMIN')")
    public ResponseEntity<Void> determineWinner(@RequestBody EndMatchRequestDTO requestDTO) {
        tournamentService.determineWinner(
                requestDTO.getTournamentId(),
                requestDTO.getMatchId(),
                requestDTO.getWinnerTeamId(),
                requestDTO.getWinnerScore(),
                requestDTO.getLoserScore()
        );

        return ResponseEntity.ok().build();
    }
}
