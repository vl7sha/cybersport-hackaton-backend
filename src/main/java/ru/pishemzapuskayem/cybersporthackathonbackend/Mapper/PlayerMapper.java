package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.CreatePlayerRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.PlayerDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Player;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlayerMapper {
    private final ModelMapper modelMapper;

    public Player map(CreatePlayerRequestDTO createPlayerRequestDTO) {
        return modelMapper.map(createPlayerRequestDTO, Player.class);
    }

    public PlayerDTO map(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

    public List<PlayerDTO> map(List<Player> players) {
        return players.stream().map(this::map).toList();
    }
}