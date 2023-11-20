package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateInvitationLinksResponse {
    private List<String> urls;
}
