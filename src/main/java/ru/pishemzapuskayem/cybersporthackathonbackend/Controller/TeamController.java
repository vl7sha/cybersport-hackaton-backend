package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.CreateTeamRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.GeneratedTeamJoinLinkResponseDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamAccountResponseDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.TeamMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.TeamService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @PostMapping
    @PreAuthorize("hasRole('CAPTAIN') or hasRole('PLAYER')")
    public ResponseEntity<GeneratedTeamJoinLinkResponseDTO> createTeam(@RequestBody CreateTeamRequestDTO requestCreateTeamDTO) {
        Team team = teamMapper.map(requestCreateTeamDTO);
        String inviteLink = teamService.createTeam(team);

        return ResponseEntity.ok(
                new GeneratedTeamJoinLinkResponseDTO(inviteLink)
        );
    }

    @PostMapping("/{teamId}")
    @PreAuthorize("hasRole('CAPTAIN')")
    public ResponseEntity<GeneratedTeamJoinLinkResponseDTO> generateNewInviteLink(@PathVariable Long teamId) {
        String inviteLink = teamService.generateNewInviteLink(teamId);
        return ResponseEntity.ok(
                new GeneratedTeamJoinLinkResponseDTO(inviteLink)
        );
    }

    @PostMapping("/join")
    @PreAuthorize("hasRole('PLAYER')")
    public ResponseEntity<Void> joinTeam(@RequestParam String token) {
        teamService.joinTeamByToken(token);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{teamId}")
    public ResponseEntity<TeamAccountResponseDTO> teamAccount(@PathVariable Long teamId){
        TeamAccountResponseDTO team = teamMapper.map(
                teamService.getTeamAccount(teamId)
        );
        return ResponseEntity.ok(team);
    }
}
