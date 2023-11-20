package ru.pishemzapuskayem.cybersporthackathonbackend.Initializers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Account;
import ru.pishemzapuskayem.cybersporthackathonbackend.Security.UserDetailsServiceImpl;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.AccountService;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    private final AccountService service;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${admin.email}")
    private String email;

    @Value("${admin.password}")
    private String password;

    @Override
    public void run(String... args) {
        try {
            UserDetails admin = userDetailsService.loadUserByUsername(email);
            logger.info("Admin " + admin.getUsername() + " already created, skipping");
        } catch (UsernameNotFoundException e) {
            String roleName = "ROLE_ADMIN";
            Account admin = new Account(
                    "amdin",
                    "admin",
                    "admin",
                    "contact",
                    password,
                    email,
                    null,
                    "admin",
                    "admin",
                    null
            );

            service.create(admin, roleName);

            logger.info("Admin created successfully");
        }
    }
}
