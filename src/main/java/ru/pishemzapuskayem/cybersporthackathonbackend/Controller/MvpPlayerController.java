package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
