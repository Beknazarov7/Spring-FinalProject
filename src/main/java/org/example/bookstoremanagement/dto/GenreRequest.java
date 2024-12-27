// File: src/main/java/org/example/bookstoremanagement/dto/GenreRequest.java

package org.example.bookstoremanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for creating or updating a Genre.
 */
@Getter
@Setter
public class GenreRequest {

    @NotBlank(message = "Genre name must not be blank.")
    private String name;
}
