package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.InvitationLink;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Role;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.InvitationLinkRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @Value("${invites.expires-in-days}")
    private int expiresInDays;

    @Transactional
    public String createInvitationLink(String roleName) {
        LocalDate expiryDate = LocalDate.from(LocalDate.now()).plusDays(expiresInDays);

        Role role = roleService.findOrCreateByName(roleName);

        InvitationLink link = new InvitationLink();
        String token = UUID.randomUUID().toString();
        link.setToken(token);
        link.setRole(role);
        link.setExpiryDate(expiryDate);
        link.setUsed(false);
        repository.save(link);

        return buildRegistrationInviteLink(token);
    }

    @Transactional
    public List<String> createInvitationLinks(int amount, String roleName) {
        LocalDate expiryDate = LocalDate.from(LocalDate.now()).plusDays(expiresInDays);

        String token;
        InvitationLink link;
        List<String> urls = new ArrayList<>();
        List<InvitationLink> links = new ArrayList<>();
        Role role = roleService.findOrCreateByName(roleName);
        for (int i = 0; i < amount; i++) {
            link = new InvitationLink();
            token = UUID.randomUUID().toString();

            link.setToken(token);
            link.setRole(role);
            link.setExpiryDate(expiryDate);
            link.setUsed(false);

            links.add(link);
            urls.add(buildRegistrationInviteLink(token));
        }

        repository.saveAll(links);

        return urls;
    }

    @Transactional
    public Role useRegisterLink(String token) {
        Optional<InvitationLink> linkOpt = repository.findByToken(token);

        if (linkOpt.isEmpty() || !isUsable(linkOpt.get())) {
            throw new ApiException("Приглашение недействительно");
        }

        InvitationLink link = linkOpt.get();
        link.setUsed(true);
        repository.save(link);

        return link.getRole();
    }

    public boolean validateLink(String token) {
        Optional<InvitationLink> linkOpt = repository.findByToken(token);
        return linkOpt.isPresent() && isUsable(linkOpt.get());
    }

    private boolean isUsable(InvitationLink link) {
        return !link.isUsed() && link.getExpiryDate().isAfter(LocalDate.now());
    }

    private String buildRegistrationInviteLink(String token) {
        return frontendRegistrationPageUrl + "?token=" + token;
    }
}
