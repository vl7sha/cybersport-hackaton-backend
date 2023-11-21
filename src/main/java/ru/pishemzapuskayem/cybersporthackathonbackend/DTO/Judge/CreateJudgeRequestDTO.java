package ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Judge;

import lombok.Getter;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateAccountRequest;

@Getter
@Setter
public class CreateJudgeRequestDTO extends CreateAccountRequest {
    private String residence;
    private Integer certificationLevel;
}
