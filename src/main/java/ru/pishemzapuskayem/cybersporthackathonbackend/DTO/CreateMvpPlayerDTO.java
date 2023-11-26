package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMvpPlayerDTO {
    private Long tournamentId;
    private Long playerId;
    private String cause;
}
