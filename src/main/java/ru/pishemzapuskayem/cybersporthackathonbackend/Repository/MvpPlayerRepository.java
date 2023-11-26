package ru.pishemzapuskayem.cybersporthackathonbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.MvpPlayer;

@Repository
public interface MvpPlayerRepository extends JpaRepository<MvpPlayer, Long> {
}
