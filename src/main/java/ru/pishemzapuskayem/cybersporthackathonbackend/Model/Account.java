package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
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
