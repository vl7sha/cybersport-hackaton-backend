package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.AbstractEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role extends AbstractEntity {
    private String name;
}
