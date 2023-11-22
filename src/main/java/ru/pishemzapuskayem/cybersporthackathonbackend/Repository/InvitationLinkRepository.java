package ru.pishemzapuskayem.cybersporthackathonbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.InvitationLink;

import java.util.Optional;

@Repository
public interface InvitationLinkRepository extends JpaRepository<InvitationLink, Long> {
    Optional<InvitationLink> findByToken(String token);
}
