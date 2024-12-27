package org.example.bookstoremanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Author name is mandatory")
    private String authorName;

    // Assuming authorId and genreId are optional. If they are mandatory, add @NotNull
    private Long authorId;
    private Long genreId;

    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price must be non-negative")
    private Double price;

    @NotNull(message = "Stock is mandatory")
    @Min(value = 0, message = "Stock must be non-negative")
    private Integer stock;
}

