package org.example.bookstoremanagement.repository;

import org.example.bookstoremanagement.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
