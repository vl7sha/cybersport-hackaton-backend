package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.TournamentRequestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Requests")
public class TournamentRequestController {

    private final TournamentRequestService service;

    //todo получение списка всех запросов с пагинацией отфильтрованному по tournamentId

    @PostMapping("/approve/{requestId}")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Void> approve(@PathVariable Long requestId) {
        service.approveJoinRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject/{requestId}")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Void> reject(@PathVariable Long requestId) {
        service.rejectJoinRequest(requestId);
        return ResponseEntity.ok().build();
    }
}
