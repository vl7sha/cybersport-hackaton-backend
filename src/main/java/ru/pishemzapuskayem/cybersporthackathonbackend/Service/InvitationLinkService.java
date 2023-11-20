package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public InvitationLink createInvitationLink(String roleName, LocalDate expiryDate) {
        Role role = roleService.findOrCreateByName(roleName);

        InvitationLink link = new InvitationLink();
        link.setToken(UUID.randomUUID().toString());
        link.setRole(role);
        link.setExpiryDate(expiryDate);
        link.setUsed(false);

        return repository.save(link);
    }

    @Transactional
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
