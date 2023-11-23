package ru.pishemzapuskayem.cybersporthackathonbackend.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Match;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentStage;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Page<Match> findByTournamentStage(TournamentStage tournamentStage, Pageable pageable);
}
