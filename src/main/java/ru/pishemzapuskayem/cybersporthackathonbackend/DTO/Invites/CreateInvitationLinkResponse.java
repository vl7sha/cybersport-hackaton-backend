package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Invites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateInvitationLinkResponse {
    private String url;
}
