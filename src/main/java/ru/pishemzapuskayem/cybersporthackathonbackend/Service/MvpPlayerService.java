package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.MvpPlayer;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.MvpPlayerRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MvpPlayerService {

    private final MvpPlayerRepository mvpPlayerRepository;

    @Transactional
    public void create(MvpPlayer mvpPlayer){
        mvpPlayerRepository.save(mvpPlayer);
    }


    public List<MvpPlayer> getAll(){
        return mvpPlayerRepository.findAll();
    }
}
