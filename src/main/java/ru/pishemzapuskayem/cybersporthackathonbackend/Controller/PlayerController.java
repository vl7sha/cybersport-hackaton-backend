package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreatePlayerRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.PlayerMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.PlayerService;

@RestController
@RequestMapping("/api/Player")
@AllArgsConstructor
public class PlayerController {

    private static final String PLAYER_ROLE = "ROLE_PLAYER";

    PlayerService playerService;
    PlayerMapper playerMapper;


    @PostMapping("/SingUp")
    public ResponseEntity<Void> singUp(CreatePlayerRequestDTO createPlayerRequestDTO){

        playerService.create(playerMapper.map(createPlayerRequestDTO), PLAYER_ROLE);

        return ResponseEntity.ok().build();
    }

}
