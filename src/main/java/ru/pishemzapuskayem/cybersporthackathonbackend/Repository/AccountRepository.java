package ru.pishemzapuskayem.cybersporthackathonbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByEmail(String email);
}
