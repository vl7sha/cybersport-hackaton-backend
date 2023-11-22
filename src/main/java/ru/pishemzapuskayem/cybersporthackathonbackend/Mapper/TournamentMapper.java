package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.CreateTournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;

@Component
@RequiredArgsConstructor
public class TournamentMapper {

    private final ModelMapper modelMapper;

    public Tournament map(CreateTournamentRequest createTournamentRequest){
        Tournament tournament = modelMapper.map(createTournamentRequest, Tournament.class);

        tournament.setChiefJudge(null);
        tournament.setJudges(null);
        tournament.setChiefSecretary(null);
        tournament.setSecretaries(null);
        tournament.setTeams(null);
        tournament.setMatches(null);
        return tournament;
    }
}
