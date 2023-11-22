package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.CreateTeamRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.TeamDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

@Component
@RequiredArgsConstructor
public class TeamMapper {
    private final ModelMapper modelMapper;
    private final PlayerMapper playerMapper;

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
}
