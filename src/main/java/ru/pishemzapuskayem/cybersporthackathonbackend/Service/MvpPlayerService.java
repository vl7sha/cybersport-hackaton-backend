package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.MvpPlayer;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.MvpPlayerRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.PlayerRepository;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MvpPlayerService {

    private final MvpPlayerRepository mvpPlayerRepository;
    private final PlayerRepository playerRepository;
    private final TournamentRepository tournamentRepository;

    @Transactional
    public void create(Long playerId, Long tournamentId, String cause){
        MvpPlayer mvpPlayer = new MvpPlayer();
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(()-> new ApiException("не найден турнир"));
        mvpPlayer.setTournament(tournament);
        tournament.setMvpPlayer(mvpPlayer);

        mvpPlayer.setTournament(tournamentRepository.findById(tournamentId).orElseThrow(()-> new ApiException("не найден турнир")));
        mvpPlayer.setPlayer(playerRepository.findById(playerId).orElseThrow(()-> new ApiException("не найден игрок")));
        mvpPlayer.setCause(cause);
        mvpPlayerRepository.save(mvpPlayer);
    }


    public List<MvpPlayer> getAll(){
        return mvpPlayerRepository.findAll();
    }
}
