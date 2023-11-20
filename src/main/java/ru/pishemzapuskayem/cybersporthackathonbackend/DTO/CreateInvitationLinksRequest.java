package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateInvitationLinksRequest {
    private Integer amount;
    private String role;
    private LocalDate expiryDate;
}
