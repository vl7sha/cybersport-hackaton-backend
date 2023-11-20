package ru.pishemzapuskayem.cybersporthackathonbackend.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.AccountRepository;


import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Account> accountOpt = accountRepository.findAccountByEmail(username);

        if (accountOpt.isEmpty()) {
            throw new UsernameNotFoundException("No find username");
        } else {
            return new UserDetailsImpl(accountOpt.get());
        }
    }
}
