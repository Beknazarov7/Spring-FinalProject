package org.example.bookstoremanagement.repository;

import org.example.bookstoremanagement.domain.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFileRepository extends JpaRepository<BookFile, Long> {
    // Additional query methods if needed
}
