package ru.pishemzapuskayem.cybersporthackathonbackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Role;
import ru.pishemzapuskayem.cybersporthackathonbackend.Repository.RoleRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository repository;

    public Role findOrCreateByName(String roleName) {
        Optional<Role> roleOpt = repository.findByName(roleName);
        return roleOpt.orElseGet(() -> createRole(roleName));
    }

    @Transactional
    public Role createRole(String roleName) {
        Role newRole = new Role();
        newRole.setName(roleName);
        return repository.save(newRole);
    }
}
