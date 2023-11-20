package ru.pishemzapuskayem.cybersporthackathonbackend.Mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.DTO.RequestCreateAccountDTO;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final ModelMapper modelMapper;

    public Account map(RequestCreateAccountDTO requestCreateAccountDTO) {
        Account account = modelMapper.map(requestCreateAccountDTO, Account.class);
        return account;
    }
}
