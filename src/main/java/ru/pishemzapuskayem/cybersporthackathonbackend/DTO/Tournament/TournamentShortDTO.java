package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentShortDTO {
    private Long id;
    private String name;
    private String discipline;
    private String organizer;
}
