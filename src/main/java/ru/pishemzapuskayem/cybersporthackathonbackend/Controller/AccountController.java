package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.RequestCreateAccountDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Mapper.AccountMapper;
import ru.pishemzapuskayem.cybersporthackathonbackend.Security.jwt.JwtUtil;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.AccountService;


@RestController
@RequestMapping("/api/Account")
public class AccountController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${jwt.tokenExpiresIn}")
    private int tokenExpiresIn;


    public AccountController(AccountMapper accountMapper, AccountService accountService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.accountMapper = accountMapper;
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/SignUp")
    public ResponseEntity<Void> register(@RequestBody RequestCreateAccountDTO requestCreateAccountDTO) {
        accountService.create(accountMapper.map(requestCreateAccountDTO));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/SignIn")
    public ResponseEntity<String> login(@RequestBody RequestCreateAccountDTO requestCreateAccountDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        requestCreateAccountDTO.getMail(),
                        requestCreateAccountDTO.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Неправильные логин или пароль");
        }

        String token = jwtUtil.generateToken(requestCreateAccountDTO.getMail(), tokenExpiresIn);

        return ResponseEntity.ok(token);
    }
}
