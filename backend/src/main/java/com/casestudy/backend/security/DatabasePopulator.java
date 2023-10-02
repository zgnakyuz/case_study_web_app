package com.casestudy.backend.security;


import com.casestudy.backend.common.enums.UserType;
import com.casestudy.backend.role.Role;
import com.casestudy.backend.role.RoleRepository;
import com.casestudy.backend.user.User;
import com.casestudy.backend.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabasePopulator {

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public DatabasePopulator(final PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void populateDatabase() {
        if (!roleRepository.existsByUserType(UserType.ROLE_USER)) {
            roleRepository.save(new Role(UserType.ROLE_USER));
        }

        if (!roleRepository.existsByUserType(UserType.ROLE_ADMIN)) {
            roleRepository.save(new Role(UserType.ROLE_ADMIN));
        }

        if (!userRepository.existsByEmail("admin@admin.com")) {
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByUserType(UserType.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Authority is not found."));
            roles.add(adminRole);

            User admin = new User("admin", "admin@admin.com",
                    passwordEncoder.encode("admin"));

            admin.setRoles(roles);
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail("zgnakyuz@gmail.com")) {
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByUserType(UserType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Authority is not found."));
            roles.add(userRole);

            User user = new User("ozgun", "zgnakyuz@gmail.com",
                    passwordEncoder.encode("ozgun"));

            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}