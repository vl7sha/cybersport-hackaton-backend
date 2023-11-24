package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Judge.CreateJudgeRequestDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.Judge.JudgeDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Judge;

@Component
@RequiredArgsConstructor
public class JudgeMapper {
    private final ModelMapper modelMapper;

    public Judge map(CreateJudgeRequestDTO createJudgeRequestDTO) {
        return modelMapper.map(createJudgeRequestDTO, Judge.class);
    }

    public JudgeDTO map(Judge judge) {
        return modelMapper.map(judge, JudgeDTO.class);
    }
}
