package ru.pishemzapuskayem.cybersporthackathonbackend.Initializers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.pishemzapuskayem.cybersporthackathonbackend.Service.RoleService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    private final RoleService service;

    @Override
    public void run(String... args) {
        List<String> roles = List.of(
                "ROLE_ADMIN",
                "ROLE_JUDGE",
                "ROLE_CAPTAIN"
        );

        for (var roleName : roles) {
            service.findOrCreateByName(roleName);
        }

        logger.info("Roles initialized successfully");
    }
}
