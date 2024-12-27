package org.example.bookstoremanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    // If null, service can default to ROLE_USER
    private String role;
}
