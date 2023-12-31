package ru.pishemzapuskayem.cybersporthackathonbackend.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentRequest;

import java.util.Optional;

@Repository
public interface TournamentRequestRepository extends JpaRepository<TournamentRequest, Long> {
    Optional<TournamentRequest> findByTournamentAndTeam(Tournament tournament, Team team);

    Page<TournamentRequest> findByTournamentAndIsApprovedIsNull(Tournament tournament, Pageable pageable);
}
