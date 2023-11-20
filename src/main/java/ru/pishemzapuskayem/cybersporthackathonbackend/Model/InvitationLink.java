package ru.pishemzapuskayem.cybersporthackathonbackend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class InvitationLink extends AbstractEntity {

    private String token;
    private LocalDate expiryDate;
    private boolean used;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}