package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.TournamentRequest.TournamentRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.TournamentRequest;

@Component
@RequiredArgsConstructor
public class TournamentRequestMapper {
    private final ModelMapper modelMapper;
    private final TournamentMapper tournamentMapper;
    private final TeamMapper teamMapper;

    public TournamentRequestDTO map(TournamentRequest tournamentRequest) {
        TournamentRequestDTO dto = modelMapper.map(tournamentRequest, TournamentRequestDTO.class);
        dto.setTournament(tournamentMapper.map(tournamentRequest.getTournament()));
        dto.setTeam(teamMapper.map(tournamentRequest.getTeam()));
        return dto;
    }
}
