package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.CreatePlayerDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Player;

@Component
@RequiredArgsConstructor
public class PlayerMapper {
    private final ModelMapper modelMapper;

    public Player map(CreatePlayerDTO createPlayerDTO) {
        return modelMapper.map(createPlayerDTO, Player.class);
    }
}