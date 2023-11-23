package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TournamentShortDTO {
    private Long id;
    private String name;
    private String discipline;
    private String organizer;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isStarted;
}
