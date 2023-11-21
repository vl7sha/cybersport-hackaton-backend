package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.CreateTeamRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

@Component
@RequiredArgsConstructor
public class TeamMapper {
    private final ModelMapper modelMapper;

    public Team map(CreateTeamRequestDTO createTeamRequestDTO) {
        Team team = modelMapper.map(createTeamRequestDTO, Team.class);
        team.setCaptain(null);
        team.setPlayers(null);
        return team;
    }
}
