package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public InvitationLink createInvitationLink(String roleName, LocalDate expiryDate) {
        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });

        InvitationLink link = new InvitationLink();
        link.setToken(UUID.randomUUID().toString());
        link.setRole(role);
        link.setExpiryDate(expiryDate);
        link.setUsed(false);

        return repository.save(link);
    }

    public boolean useLink(String token) {
        Optional<InvitationLink> linkOpt = repository.findByToken(token);
        if (linkOpt.isPresent()) {
            InvitationLink link = linkOpt.get();
            if (isUsable(link)) {
                link.setUsed(true);
                repository.save(link);
                return true;
            }
        }
        return false;
    }

    private boolean isUsable(InvitationLink link) {
        return !link.isUsed() && link.getExpiryDate().isAfter(LocalDate.now());
    }
}
