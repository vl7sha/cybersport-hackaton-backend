package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Players;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateAccountRequest;

@Getter
@Setter
public class CreatePlayerRequestDTO extends CreateAccountRequest {
    private String gender;
    private String rank;
    private String gto;
}
