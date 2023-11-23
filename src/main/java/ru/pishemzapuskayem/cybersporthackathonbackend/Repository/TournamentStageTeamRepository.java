package ru.pishemzapuskayem.cybersporthackathonbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentStageTeam;

@Repository
public interface TournamentStageTeamRepository extends JpaRepository<TournamentStageTeam, Long> {
}
