package ru.pishemzapuskayem.cybersporthackathonbackend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.BasicAuth.AuthRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.BasicAuth.AuthResponseDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Exceptions.ApiException;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Account;
import ru.pishemzapuskayem.cybersporthackathonbackend.Security.UserDetailsImpl;
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
    public ResponseEntity<AuthResponseDTO> logIn(@RequestBody AuthRequestDTO authRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.getEmail(),
                        authRequestDTO.getPassword());

        String role = "ROLE_ANONYMOUS";
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            role = ((UserDetailsImpl)authentication.getPrincipal()).getAccount().getRole().getName();
        } catch (BadCredentialsException e) {
            throw new ApiException("Неправильные логин или пароль");
        }

        String token = jwtUtil.generateToken(authRequestDTO.getEmail(), tokenExpiresIn);

        return ResponseEntity.ok(new AuthResponseDTO(
                token,
                tokenExpiresIn,
                role
        ));
    }
}
