package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role extends AbstractEntity {
    private String name;
}
