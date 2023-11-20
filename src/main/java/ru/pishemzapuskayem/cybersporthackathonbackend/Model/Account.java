package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
