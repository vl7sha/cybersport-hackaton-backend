package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateMvpPlayerDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.MvpPlayerDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.MvpPlayerMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.MvpPlayerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mvp")
public class MvpPlayerController {

    private final MvpPlayerService mvpPlayerService;
    private final MvpPlayerMapper mvpPlayerMapper;

    @GetMapping("/list")
    public ResponseEntity<List<MvpPlayerDTO>> getAll(){
        return ResponseEntity.ok(
                mvpPlayerMapper.map(mvpPlayerService.getAll())
        );
    }

    @PostMapping
    public  ResponseEntity<Void> setMvp(@RequestBody CreateMvpPlayerDTO request) {
        mvpPlayerService.create(request.getPlayerId(), request.getTournamentId(), request.getCause());
        return ResponseEntity.ok().build();
    }
}
