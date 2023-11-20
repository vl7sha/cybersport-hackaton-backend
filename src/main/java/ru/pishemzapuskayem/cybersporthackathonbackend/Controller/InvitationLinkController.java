package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateInvitationLinkRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateInvitationLinkResponse;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.InvitationLinkService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Invites")
public class InvitationLinkController {

    private final InvitationLinkService invitationLinkService;

    @GetMapping
    public ResponseEntity<Boolean> validateLink(@RequestParam String token) {
        boolean isValid = invitationLinkService.validateLink(token);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateInvitationLinkResponse> createInvitationLink(@RequestBody CreateInvitationLinkRequest request) {
        String registrationPageUrl = invitationLinkService.createInvitationLink(request.getRole(), request.getExpiryDate());
        return ResponseEntity.ok(
                new CreateInvitationLinkResponse(registrationPageUrl)
        );
    }
}
