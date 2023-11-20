package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.AccountRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Security.UserDetailsImpl;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account getAuthenticated() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userDetails.getAccount();
    }

    @Transactional
    public void create(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        accountRepository.save(account);
    }
}
