package org.example.bookstoremanagement.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required.")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Role is required.")
    private String role; // e.g. ROLE_ADMIN, ROLE_USER
}
