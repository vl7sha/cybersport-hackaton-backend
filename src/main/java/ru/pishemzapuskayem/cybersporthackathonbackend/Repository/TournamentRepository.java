package ru.pishemzapuskayem.cybersporthackathonbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;

import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    Optional<List<Tournament>> findTournamentsByDiscipline (String discipline);
}
