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
import ru.pishemzapuskayem.cybersporthackathonbackend.Model.Role;
import ru.pishemzapuskayem.cybersporthackathonbackend.Security.UserDetailsServiceImpl;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.AccountService;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    private final AccountService service;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Override
    public void run(String... args) {
        try {
            UserDetails admin = userDetailsService.loadUserByUsername(username);
            logger.info("Admin " + admin.getUsername() + " already created, skipping");
        } catch (UsernameNotFoundException e) {
            Role role = new Role();
            role.setName("ROLE_ADMIN");

            Account admin = new Account(
                    username,
                    "admin",
                    "admin",
                    "admin@admin.ru",
                    password,
                    "admin@admin.ru",
                    null,
                    "admin",
                    "admin",
                    role
            );

            service.create(admin);

            logger.info("Admin created successfully");
        }
    }
}
