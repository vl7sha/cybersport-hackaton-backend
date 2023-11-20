package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role extends AbstractEntity {
    private String name;
}
