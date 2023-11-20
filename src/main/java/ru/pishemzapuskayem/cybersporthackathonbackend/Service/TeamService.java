package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.PlayerRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TeamRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {
    private final TeamRepository teamRepository;
    private final AccountService accountService;
    private final PlayerRepository playerRepository;

    public void createTeam(Team team) {
        //todo получить капитана ....
        //todo сохранить игроков ...
        teamRepository.save(team);
    }
}
