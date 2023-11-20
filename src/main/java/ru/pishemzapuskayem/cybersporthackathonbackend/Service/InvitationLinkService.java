package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.InvitationLink;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Role;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.InvitationLinkRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvitationLinkService {

    private final InvitationLinkRepository repository;
    private final RoleService roleService;

    @Value("${urls.frontend.registration-page}")
    private String frontendRegistrationPageUrl;

    @Transactional
    public String createInvitationLink(String roleName, LocalDate expiryDate) {
        Role role = roleService.findOrCreateByName(roleName);

        InvitationLink link = new InvitationLink();
        String token = UUID.randomUUID().toString();
        link.setToken(token);
        link.setRole(role);
        link.setExpiryDate(expiryDate);
        link.setUsed(false);
        repository.save(link);

        return buildInviteLink(token);
    }

    @Transactional
    public void useLink(String token) {
        Optional<InvitationLink> linkOpt = repository.findByToken(token);

        if (linkOpt.isEmpty() || !isUsable(linkOpt.get())) {
            throw new ApiException("Приглашение недействительно");
        }

        InvitationLink link = linkOpt.get();
        link.setUsed(true);
        repository.save(link);
    }

    public boolean validateLink(String token) {
        Optional<InvitationLink> linkOpt = repository.findByToken(token);
        return linkOpt.isPresent() && isUsable(linkOpt.get());
    }

    private boolean isUsable(InvitationLink link) {
        return !link.isUsed() && link.getExpiryDate().isAfter(LocalDate.now());
    }

    private String buildInviteLink(String token) {
        return frontendRegistrationPageUrl + "?token=" + token;
    }
}
