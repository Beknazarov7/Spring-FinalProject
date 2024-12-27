package org.example.bookstoremanagement.repository;

import org.example.bookstoremanagement.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Book> findByStockGreaterThanEqual(Integer stock);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author.name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<Book> findByAuthorName(String authorName);

    @Query("SELECT b FROM Book b WHERE LOWER(b.genre.name) LIKE LOWER(CONCAT('%', :genreName, '%'))")
    List<Book> findByGenreName(String genreName);
}
