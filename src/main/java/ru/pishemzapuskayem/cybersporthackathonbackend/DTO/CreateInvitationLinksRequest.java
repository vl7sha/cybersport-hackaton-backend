package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInvitationLinksRequest {
    private Integer amount;
    private String role;
}
