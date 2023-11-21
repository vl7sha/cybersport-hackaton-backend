package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateAccountRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Security.jwt.JwtUtil;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Account")
public class AccountController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${jwt.tokenExpiresIn}")
    private int tokenExpiresIn;

    @PostMapping("/SignIn")
    public ResponseEntity<String> logIn(@RequestBody CreateAccountRequest createAccountRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        createAccountRequest.getEmail(),
                        createAccountRequest.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Неправильные логин или пароль");
        }

        String token = jwtUtil.generateToken(createAccountRequest.getEmail(), tokenExpiresIn);

        return ResponseEntity.ok(token);
    }
}
