package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Teams.CreateTeamRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Player;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Team;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeamMapper {
    private final ModelMapper modelMapper;
    private final PlayerMapper playerMapper;

    public Team map(CreateTeamRequestDTO createTeamRequestDTO) {
        Team team = modelMapper.map(createTeamRequestDTO, Team.class);

        if (createTeamRequestDTO.getPlayers() != null) {
            List<Player> players = createTeamRequestDTO.getPlayers().stream()
                    .map(playerMapper::map)
                    .collect(Collectors.toList());
            team.setPlayers(players);
        }

        return team;
    }
}
