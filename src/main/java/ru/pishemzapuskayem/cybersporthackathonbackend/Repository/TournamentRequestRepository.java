package ru.pishemzapuskayem.cybersporthackathonbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.TournamentRequest;

import java.util.Optional;

@Repository
public interface TournamentRequestRepository extends JpaRepository<TournamentRequest, Long> {
    Optional<TournamentRequest> findByTournamentAndTeam(Tournament tournament, Team team);
}
