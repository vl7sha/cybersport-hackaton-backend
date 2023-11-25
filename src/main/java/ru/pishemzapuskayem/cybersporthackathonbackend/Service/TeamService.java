package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.CreateTeamRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Player;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentResult;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.PlayerRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TeamRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentResultRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.SearchCriteria.XPage;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final RoleService roleService;

    @Value("${urls.frontend.join-team-page}")
    private String frontendJoinTeamPageUrl;
    private final TournamentResultRepository tournamentResultRepository;

    @Transactional
    public String createTeam(Team team) {
        Player captain = getAuthenticated();
        team.setCaptain(captain);
        tryUpdateRole(captain, "ROLE_CAPTAIN");
        String token = UUID.randomUUID().toString();
        team.setInvitationToken(token);

        teamRepository.save(team);

        return buildJoinTeamInviteLink(token);
    }

    @Transactional
    public void tryUpdateRole(Player player, String roleName) {
        if (!Objects.equals(player.getRole().getName(), roleName)) {
            player.setRole(roleService.findOrCreateByName(roleName));
            playerRepository.save(player);
        }
    }

    @Transactional
    public String generateNewInviteLink(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException("Команда не найдена"));

        String token = UUID.randomUUID().toString();
        team.setInvitationToken(token);
        teamRepository.save(team);

        return buildJoinTeamInviteLink(token);
    }

    @Transactional
    public void joinTeamByToken(String token) {
        Team team = teamRepository.findByInvitationToken(token)
                .orElseThrow(() -> new ApiException("Команда по этому приглашению не найдена"));

        Player authenticatedPlayer = getAuthenticated();
        List<Player> players = team.getPlayers();

        if (players.contains(authenticatedPlayer)) {
            throw new ApiException("Вы уже вступили в команду");
        }

        if (Objects.equals(team.getCaptain(), authenticatedPlayer)) {
            throw new ApiException("Вы уже вступили в команду как капитан");
        }

        players.add(authenticatedPlayer);

        teamRepository.save(team);
    }

    private String buildJoinTeamInviteLink(String token) {
        return frontendJoinTeamPageUrl + "?token=" + token;
    }

    private Player getAuthenticated() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return playerRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Аккаунт игрока не найден"));
    }

    public List<TournamentResult> getTeamAccount(Long id) {
         Team team = teamRepository.findById(id).orElseThrow(() -> new ApiException("Этой команды нет"));

        return tournamentResultRepository.findByTeam(team)
                 .orElseThrow(()-> new ApiException("Эта команда еще не где не участвовала"));
    }

    public Page<Team> getTeams(XPage page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getItemsPerPage());
        Page<Team> teams = teamRepository.findAll(pageable);

        teams.forEach(t-> {
            Hibernate.initialize(t.getPlayers());
            Hibernate.initialize(t.getResults());
        });

        return teams;
    }

    public Team getTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException("Команда не найдена"));

        Hibernate.initialize(team.getPlayers());
        Hibernate.initialize(team.getResults());

        return team;
    }
}
