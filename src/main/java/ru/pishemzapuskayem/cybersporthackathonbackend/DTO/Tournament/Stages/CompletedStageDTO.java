package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.Stages;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompletedStageDTO {
    private String name;
    private List<CompletedMatchDTO> matches;
}
