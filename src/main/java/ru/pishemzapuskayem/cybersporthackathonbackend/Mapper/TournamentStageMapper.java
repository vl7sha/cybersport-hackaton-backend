package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Matches.MatchDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.TournamentStageDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.TournamentStage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TournamentStageMapper {
    private final ModelMapper modelMapper;
    private final MatchMapper matchMapper;

    public TournamentStageDTO map(TournamentStage currentStage) {
        TournamentStageDTO dto = modelMapper.map(currentStage, TournamentStageDTO.class);
        List<MatchDTO> matches = currentStage.getMatches()
                .stream()
                .map(matchMapper::map)
                .toList();
        dto.setMatches(matches);
        return dto;
    }
}
