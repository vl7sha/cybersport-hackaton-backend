package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.TournamentResultShortDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TournamentResultMapper {

    private final ModelMapper modelMapper;

    public TournamentResultShortDTO map(TournamentResult tournamentResult) {
        TournamentResultShortDTO dto = modelMapper.map(tournamentResult, TournamentResultShortDTO.class);
        dto.setTeamId(tournamentResult.getTournament().getId());
        return dto;
    }

    public List<TournamentResultShortDTO> map(List<TournamentResult> tournamentResults) {
        return tournamentResults.stream().map(this::map).toList();
    }
}
