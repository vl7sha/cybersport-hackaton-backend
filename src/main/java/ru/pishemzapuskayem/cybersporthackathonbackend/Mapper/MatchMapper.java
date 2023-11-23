package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Matches.MatchDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Match;

@Component
@RequiredArgsConstructor
public class MatchMapper {
    private final ModelMapper modelMapper;
    private final TeamMapper teamMapper;

    public MatchDTO map(Match match) {
        MatchDTO dto = modelMapper.map(match, MatchDTO.class);
        dto.setTeam1(teamMapper.map(match.getTeam1()));
        dto.setTeam2(teamMapper.map(match.getTeam2()));
        dto.setWinnerTeam(teamMapper.map(match.getWinnerTeam()));

        return dto;
    }
}
