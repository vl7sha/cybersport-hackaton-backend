package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.CreateTeamRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.TeamMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.TeamService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @PostMapping
    @PreAuthorize("hasRole('CAPTAIN')")
    public ResponseEntity<Void> createTeam(@RequestBody CreateTeamRequestDTO requestCreateTeamDTO) {
        Team team = teamMapper.map(requestCreateTeamDTO);
        teamService.createTeam(team);

        return ResponseEntity.ok().build();
    }
}
