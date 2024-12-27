// File: src/main/java/org/example/bookstoremanagement/dto/AuthorRequest.java

package org.example.bookstoremanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for creating or updating an Author.
 */
@Getter
@Setter
public class AuthorRequest {

    @NotBlank(message = "Author name must not be blank.")
    private String name;

    private String bio;
}
