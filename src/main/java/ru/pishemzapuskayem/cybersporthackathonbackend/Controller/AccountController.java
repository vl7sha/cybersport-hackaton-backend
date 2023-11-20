package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.RequestCreateAccountDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.AccountMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Role;
import ru.pishemzapuskayem.cybersporthackathonbackend.Security.jwt.JwtUtil;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.AccountService;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.InvitationLinkService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Account")
public class AccountController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final InvitationLinkService invitationLinkService;

    @Value("${jwt.tokenExpiresIn}")
    private int tokenExpiresIn;

    @PostMapping("/SignUp")
    public ResponseEntity<Void> register(@RequestParam String token,
                                         @RequestBody RequestCreateAccountDTO requestCreateAccountDTO
    ) {
        Role role = invitationLinkService.useLink(token);
        accountService.create(accountMapper.map(requestCreateAccountDTO), role);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/SignIn")
    public ResponseEntity<String> logIn(@RequestBody RequestCreateAccountDTO requestCreateAccountDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        requestCreateAccountDTO.getEmail(),
                        requestCreateAccountDTO.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Неправильные логин или пароль");
        }

        String token = jwtUtil.generateToken(requestCreateAccountDTO.getEmail(), tokenExpiresIn);

        return ResponseEntity.ok(token);
    }
}
