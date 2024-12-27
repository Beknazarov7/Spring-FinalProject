package org.example.bookstoremanagement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoremanagement.domain.User;
import org.example.bookstoremanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserService userService;

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        log.info("Admin fetching user details for: {}", username);
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    // Additional admin-only endpoints can go here
}
