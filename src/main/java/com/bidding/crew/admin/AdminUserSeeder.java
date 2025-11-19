package com.bidding.crew.admin;

import com.bidding.crew.user.AccountUser;
import com.bidding.crew.user.Role;
import com.bidding.crew.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserSeeder {

    @Bean
    CommandLineRunner seedAdminUser(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder) {
        return args -> {
            String adminUsernameEnv = System.getenv("ADMIN_USERNAME");
            System.out.println("DEBUG ADMIN_USERNAME from env = " + adminUsernameEnv);
            String adminUsername = adminUsernameEnv != null ? adminUsernameEnv : "admin";
            System.out.println("Using admin username = " + adminUsername);
            String adminPassword = System.getenv().getOrDefault("ADMIN_PASSWORD", "changeme123");
            boolean adminExists = userRepository.findByUsername(adminUsername).isPresent();

            if (!adminExists) {
                AccountUser admin = new AccountUser();
                admin.setUsername(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);

                System.out.println("Seeded ADMIN user: " + adminUsername);
            } else {
                System.out.println("ADMIN user already exists, skipping seeding");
            }
        };
    }
}
