package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Pagination.PageDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.TournamentRequest.TournamentRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.TournamentRequestMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.SearchCriteria.XPage;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.TournamentRequestService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Requests")
public class TournamentRequestController {

    private final TournamentRequestService service;
    private final TournamentRequestMapper mapper;

    @GetMapping("/{tournamentId}")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<PageDTO<TournamentRequestDTO>> index(@PathVariable Long tournamentId, XPage page) {
        Page<TournamentRequestDTO> dtos =
                service.findPendingRequestsByTournamentId(page, tournamentId)
                .map(mapper::map);

        return ResponseEntity.ok(
                new PageDTO<>(
                        dtos.getContent(),
                        dtos.getTotalElements()
                )
        );
    }

    @PostMapping("/{requestId}/approve")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Void> approve(@PathVariable Long requestId) {
        service.approveJoinRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{requestId}/reject")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Void> reject(@PathVariable Long requestId) {
        service.rejectJoinRequest(requestId);
        return ResponseEntity.ok().build();
    }
}
