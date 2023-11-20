package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.InvitationLink;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Role;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.InvitationLinkRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.RoleRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InvitationLinkService {

    private final InvitationLinkRepository repository;
    private final RoleRepository roleRepository;

    @Value("${urls.frontend.registration-page}")
    private String frontendRegistrationPageUrl;

    public String createInvitationLink(String roleName, LocalDate expiryDate) {
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });

        InvitationLink link = new InvitationLink();
        String token = UUID.randomUUID().toString();
        link.setToken(token);
        link.setRole(role);
        link.setExpiryDate(expiryDate);
        link.setUsed(false);
        repository.save(link);

        return buildInviteLink(token);
    }

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
