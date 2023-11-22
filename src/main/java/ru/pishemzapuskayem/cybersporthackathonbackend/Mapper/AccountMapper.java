package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.CreateAccountRequest;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account.Account;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final ModelMapper modelMapper;

    public Account map(CreateAccountRequest createAccountRequest) {
        return modelMapper.map(createAccountRequest, Account.class);
    }
}
