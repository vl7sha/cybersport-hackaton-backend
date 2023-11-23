package ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("0")
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.INTEGER)
public class Account extends Person {

    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Account(String email, String password, Role role, List<String> contacts) {
        super(contacts);

        this.email = email;
        this.password = password;
        this.role = role;
    }
}
