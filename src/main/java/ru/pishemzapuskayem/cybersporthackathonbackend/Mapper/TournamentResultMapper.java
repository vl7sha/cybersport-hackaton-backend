package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.TournamentResultDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TournamentResultMapper {

    private final ModelMapper modelMapper;

    public TournamentResultDTO map(TournamentResult tournamentResult) {
        return modelMapper.map(tournamentResult, TournamentResultDTO.class);
    }

    public List<TournamentResultDTO> map(List<TournamentResult> tournamentResults) {
        return tournamentResults.stream().map(this::map).toList();
    }
}
