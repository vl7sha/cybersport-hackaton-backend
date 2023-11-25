package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.BasicAuth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthResponseDTO {
    private String token;
    private Integer expiresIn;
    private String role;
}
