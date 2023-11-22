package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.AddJudgesTournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateTournamentRequest;
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

    @PostMapping("/{tournamentId}/addJudges")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Void> addJudges(@PathVariable Long tournamentId,
                                         @RequestBody AddJudgesTournamentRequest addJudgeTournamentRequest) {

        tournamentService.addJudges(tournamentId,addJudgeTournamentRequest);
        return ResponseEntity.ok().build();
    }

}
