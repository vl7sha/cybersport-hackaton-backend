package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateTournamentRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.TournamentMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.TournamentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Tournament")
public class TournamentController {

    TournamentService tournamentService;
    TournamentMapper tournamentMapper;

    @PostMapping("/create")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Void> create(@RequestParam CreateTournamentRequest createTournamentRequest){
        tournamentService.create(tournamentMapper.map(createTournamentRequest));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<Void> update(@RequestParam CreateTournamentRequest createTournamentRequest){
        tournamentService.update(tournamentMapper.map(createTournamentRequest));
        return ResponseEntity.ok().build();
    }


    @GetMapping("/list")
    public ResponseEntity<List<Void>> getTournamentLists(){
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Void> getTournament(@RequestParam String id){
        return ResponseEntity.ok().build();
    }
}
