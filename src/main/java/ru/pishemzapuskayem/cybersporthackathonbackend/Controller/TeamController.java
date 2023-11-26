package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Pagination.PageDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamShortDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.CreateTeamRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.GeneratedTeamJoinLinkResponseDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.TeamMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.SearchCriteria.XPage;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.TeamService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Teams")
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @GetMapping
    public ResponseEntity<PageDTO<TeamDTO>> getTeams(XPage page) {
        Page<TeamDTO> dtos = teamService.getTeams(page)
                .map(teamMapper::map);

        return ResponseEntity.ok(
                new PageDTO<>(
                        dtos.getContent(),
                        dtos.getTotalElements()
                )
        );
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long teamId) {
        Team team = teamService.getTeamById(teamId);
        return ResponseEntity.ok(teamMapper.map(team));
    }

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

    @PostMapping("/{discipline/ranking}")
    public ResponseEntity<List<TeamShortDTO>> disciplineRanking(@PathVariable String discipline){
        TeamShortDTO teamShortDTO = teamMapper.map(teamService.teamsRankingByDiscipline(discipline));

        return ResponseEntity.ok(teamShortDTO);
    }

//    @GetMapping("/{teamId}")
//    public ResponseEntity<TeamAccountResponseDTO> teamAccount(@PathVariable Long teamId){
//        TeamAccountResponseDTO team = teamMapper.map(
//                teamService.getTeamAccount(teamId)
//        );
//        return ResponseEntity.ok(team);
//    }
}
