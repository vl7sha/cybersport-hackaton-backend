package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account extends AbstractEntity {

    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String secondName;
    @Column
    private String contact;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private LocalDate datOfBirth;
    @Column
    private String theSubjectOfTheRF;
    @Column
    private String city;
    @Column
    private String role;

}
