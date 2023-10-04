package com.casestudy.backend.registration;

import com.casestudy.backend.common.enums.UserType;
import com.casestudy.backend.common.response.MessageResponse;
import com.casestudy.backend.role.Role;
import com.casestudy.backend.role.RoleRepository;
import com.casestudy.backend.security.jwt.JwtUtils;
import com.casestudy.backend.user.User;
import com.casestudy.backend.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    public RegistrationController(final AuthenticationManager authenticationManager, final UserRepository userRepository,
                                  final RoleRepository roleRepository, final PasswordEncoder encoder, final JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping()
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(registrationRequest.getUsername(), registrationRequest.getEmail(),
                encoder.encode(registrationRequest.getPassword()), 100);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByUserType(UserType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}