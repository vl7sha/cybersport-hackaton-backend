package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Judge;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Role;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.AccountRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.JudgeRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JudgeService {

    private final JudgeRepository repository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(Judge judge, Role role) {
        accountRepository.findAccountByEmail(judge.getEmail()).ifPresent((value) -> {
            throw new ApiException("Аккаунт с таким email уже существует", HttpStatus.CONFLICT);
        });

        judge.setRole(role);
        judge.setPassword(passwordEncoder.encode(judge.getPassword()));
        repository.save(judge);
    }
}
