package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Tournament.Judge;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddJudgesTournamentRequest {
    List<Long> idJudges;
}
