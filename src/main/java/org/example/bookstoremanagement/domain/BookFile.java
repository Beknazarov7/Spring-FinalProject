package org.example.bookstoremanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String contentType;
    private Long fileSize;

    // The actual file data stored in DB
    @Lob
    private byte[] data;

    // Link back to Book (OneToOne)
    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}
