package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCreateAccountDTO {
    private String mail;
    private String password;
    private String theSubjectOfTheRF;
}
