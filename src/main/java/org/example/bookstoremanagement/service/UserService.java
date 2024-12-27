package org.example.bookstoremanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoremanagement.domain.User;
import org.example.bookstoremanagement.exception.ResourceNotFoundException;
import org.example.bookstoremanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String rawPassword, String role) {
        log.info("Registering user: {}", username);

        Optional<User> existing = userRepository.findByUsername(username);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        if (role == null || role.isBlank()) {
            role = "ROLE_USER";
        }

        String encoded = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .username(username)
                .password(encoded)
                .role(role)
                .build();

        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }
}
