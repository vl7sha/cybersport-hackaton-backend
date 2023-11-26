package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.CreateTeamRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamAccountResponseDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamShortDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TeamMapper {
    private final ModelMapper modelMapper;
    private final PlayerMapper playerMapper;
    private final TournamentResultMapper tournamentResultMapper;

    public Team map(CreateTeamRequestDTO createTeamRequestDTO) {
        Team team = modelMapper.map(createTeamRequestDTO, Team.class);
        team.setCaptain(null);
        team.setPlayers(null);
        return team;
    }

    public TeamDTO map(Team team) {
        TeamDTO dto = modelMapper.map(team, TeamDTO.class);
        dto.setCaptain(playerMapper.map(team.getCaptain()));
        dto.setPlayers(playerMapper.map(team.getPlayers()));
        return dto;
    }

    public TeamAccountResponseDTO map(List<TournamentResult> tournamentResult) {
        TeamAccountResponseDTO teamAccountResponseDTO = modelMapper.map(tournamentResult.get(0).getTeam(),
                TeamAccountResponseDTO.class);

        teamAccountResponseDTO.setResults(
                tournamentResultMapper.map(tournamentResult)
        );

        return teamAccountResponseDTO;
    }

    public List<TeamShortDTO> map(List<Team> teams) {

        List<TeamShortDTO> teamShortDTOS = new ArrayList<>();

        for (Team team:
             teams) {
            TeamShortDTO teamShortDTO = new TeamShortDTO();

            teamShortDTO.setTeam(map(team));
        }

        return  teamShortDTOS;

    }
}
