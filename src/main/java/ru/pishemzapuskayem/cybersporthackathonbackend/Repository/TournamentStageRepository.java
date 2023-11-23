package ru.pishemzapuskayem.cybersporthackathonbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentStage;

@Repository
public interface TournamentStageRepository extends JpaRepository<TournamentStage, Long> {
}
