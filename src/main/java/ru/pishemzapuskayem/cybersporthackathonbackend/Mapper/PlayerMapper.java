package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.CreatePlayerRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Player;

@Component
@RequiredArgsConstructor
public class PlayerMapper {
    private final ModelMapper modelMapper;

    public Player map(CreatePlayerRequestDTO createPlayerRequestDTO) {
        return modelMapper.map(createPlayerRequestDTO, Player.class);
    }
}