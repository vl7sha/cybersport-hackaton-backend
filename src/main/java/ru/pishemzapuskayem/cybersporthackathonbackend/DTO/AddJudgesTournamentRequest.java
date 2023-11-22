package ru.pishemzapuskayem.cybersporthackathonbackend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddJudgesTournamentRequest {
    List<String> emails;
}
