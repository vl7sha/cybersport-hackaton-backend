package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Tournament;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.TournamentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentService {

    TournamentRepository tournamentRepository;

    public void create(Tournament tournament){
       tournamentRepository.save(tournament);
    }

    public void update(Tournament tournament){
        tournamentRepository.save(tournament);
    }

    public Tournament getById(Long id){
        return tournamentRepository.findById(id).orElseThrow(() -> new ApiException("Такого турнира нет"));
    }

    public List<Tournament> get(){
        return  tournamentRepository.findAll();
    }
}
