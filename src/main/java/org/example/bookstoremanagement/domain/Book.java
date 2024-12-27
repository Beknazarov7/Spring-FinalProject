package org.example.bookstoremanagement.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title must not be blank.")
    private String title;

    @Column(nullable = false)
    private String authorName; // or you could reference an Author entity

    private Double price;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = true)
    private Author author; // New relationship with Author



    /**
     * Optional relationship to BookFile for storing file data.
     * If you want multiple files per book, you can use a @OneToMany.
     */
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private BookFile bookFile;

}
