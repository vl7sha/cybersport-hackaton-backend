package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.MvpPlayerDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.CreatePlayerRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.MvpPlayer;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Player;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MvpPlayerMapper {

    private final ModelMapper modelMapper;

    public List<MvpPlayerDTO> map(List<MvpPlayer> mvpPlayers){
        List<MvpPlayerDTO> mvpPlayerDTOS = new ArrayList<>();

        for (var mvp:
             mvpPlayers) {
            mvpPlayerDTOS.add(map(mvp));
        }

        return mvpPlayerDTOS;
    }

    public MvpPlayerDTO map(MvpPlayer createPlayerRequestDTO) {
        return modelMapper.map(createPlayerRequestDTO, MvpPlayerDTO.class);
    }
}
