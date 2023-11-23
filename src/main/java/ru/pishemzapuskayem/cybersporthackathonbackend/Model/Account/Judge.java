package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("1")
public class Judge extends Account {

    private String residence;
    private Integer certificationLevel;
}
