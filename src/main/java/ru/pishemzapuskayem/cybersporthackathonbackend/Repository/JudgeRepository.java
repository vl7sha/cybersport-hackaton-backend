package ru.pishemzapuskayem.cybersporthackathonbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Judge;

import java.util.Optional;

@Repository
public interface JudgeRepository extends JpaRepository<Judge, Long> {
    Optional<Judge> findByEmail(String email);
}
