package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePlayerRequest extends CreateAccountRequest{
    private String gender;
    private String rank;
    private String gto;
}
