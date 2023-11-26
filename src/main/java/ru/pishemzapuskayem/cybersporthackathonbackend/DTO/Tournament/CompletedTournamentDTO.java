package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.Stages.CompletedStageDTO;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CompletedTournamentDTO {
    private String name;
    private String location;
    private String discipline;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate registrationDate;
    private LocalDate closingDate;
    private LocalDate reportDate;
    private String organizer;
    private List<CompletedStageDTO> stages;
    private List<Long> judgeIds;
    private List<Long> secretaryIds;
    private Long chiefJudgeId;
    private Long chiefSecretaryId;
    private List<CompletedTournamentResultDTO> results;
}
