package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Player;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.AccountRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.PlayerRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PlayerService {

    PlayerRepository playerRepository;
    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;
    RoleService roleService;

    @Transactional
    public void create(Player player, String role) {
        accountRepository.findAccountByEmail(player.getEmail()).ifPresent((value) -> {
            throw new ApiException("Аккаунт с таким email уже существует", HttpStatus.CONFLICT);
        });

        player.setRole(roleService.findOrCreateByName(role));
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        playerRepository.save(player);
    }
}
