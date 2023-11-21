package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players.CreatePlayerRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.PlayerMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.PlayerService;

@RestController
@RequestMapping("/api/Player")
@RequiredArgsConstructor
public class PlayerController {

    private static final String PLAYER_ROLE = "ROLE_PLAYER";

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;


    @PostMapping("/SingUp")
    public ResponseEntity<Void> singUp(CreatePlayerRequestDTO createPlayerRequestDTO){
        playerService.create(playerMapper.map(createPlayerRequestDTO), PLAYER_ROLE);
        return ResponseEntity.ok().build();
    }
}
