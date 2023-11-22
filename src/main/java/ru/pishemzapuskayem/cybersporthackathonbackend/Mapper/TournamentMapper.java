package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateTournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.TournamentShortDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;

@Component
@RequiredArgsConstructor
public class TournamentMapper {

    private final ModelMapper modelMapper;

    public Tournament map(CreateTournamentRequest createTournamentRequest){
        return modelMapper.map(createTournamentRequest, Tournament.class);
    }

    public TournamentShortDTO map(Tournament tournament) {
        return modelMapper.map(tournament, TournamentShortDTO.class);
    }
}
