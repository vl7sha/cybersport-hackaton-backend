package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Judge.CreateJudgeRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.JudgeMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Role;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.InvitationLinkService;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.JudgeService;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Judge")
public class JudgeController {

    private static final String JUDGE_ROLE = "ROLE_JUDGE";

    private final JudgeMapper mapper;
    private final JudgeService judgeService;
    private final InvitationLinkService invitationLinkService;

    @PostMapping("/SignUp")
    public ResponseEntity<Void> register(@RequestParam String token,
                                         @RequestBody CreateJudgeRequestDTO createJudgeRequestDTO
    ) {
        Role role = invitationLinkService.useRegisterLink(token);
        if (!Objects.equals(role.getName(), JUDGE_ROLE)) {
            throw new ApiException("Вы не можете зарегистрироваться как судья");
        }

        judgeService.create(mapper.map(createJudgeRequestDTO), role);

        return ResponseEntity.ok().build();
    }
}
