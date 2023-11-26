package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Matches.MatchDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.Stages.CompletedMatchDTO;
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

        if (match.getWinnerTeam() != null) {
            dto.setWinnerTeam(teamMapper.map(match.getWinnerTeam()));
        }

        return dto;
    }

    public Match map(CompletedMatchDTO completedMatchDTO) {
        Match match = modelMapper.map(completedMatchDTO, Match.class);

        match.setTeam1(teamMapper.map(completedMatchDTO.getTeam1Id()));
        match.setTeam2(teamMapper.map(completedMatchDTO.getTeam2Id()));

        if (completedMatchDTO.getWinnerTeamId() != null) {
            match.setWinnerTeam(teamMapper.map(completedMatchDTO.getWinnerTeamId()));
        }

        return match;
    }
}
