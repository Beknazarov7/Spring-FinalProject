package org.example.bookstoremanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoremanagement.domain.Author;
import org.example.bookstoremanagement.dto.AuthorRequest;
import org.example.bookstoremanagement.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles CRUD operations for Author.
 */
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    /**
     * Creates a new Author.
     *
     * @param authorRequest the request body containing author details
     * @return the created Author
     */
    @PostMapping
    public ResponseEntity<Author> createAuthor(
            @Valid @RequestBody AuthorRequest authorRequest
    ) {
        log.info("REST request to create author: {}", authorRequest.getName());
        Author author = authorService.createAuthor(authorRequest.getName(), authorRequest.getBio());
        return ResponseEntity.ok(author);
    }

    /**
     * Retrieves an Author by ID.
     *
     * @param id the ID of the Author
     * @return the Author with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        log.info("REST request to get author by ID: {}", id);
        Author author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    /**
     * Retrieves all Authors.
     *
     * @return a list of all Authors
     */
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        log.info("REST request to get all authors");
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    /**
     * Updates an existing Author.
     *
     * @param id            the ID of the Author to update
     * @param authorRequest the request body containing updated author details
     * @return the updated Author
     */
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequest authorRequest
    ) {
        log.info("REST request to update author ID: {}", id);
        Author updated = authorService.updateAuthor(id, authorRequest.getName(), authorRequest.getBio());
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes an Author by ID.
     *
     * @param id the ID of the Author to delete
     * @return a confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        log.info("REST request to delete author ID: {}", id);
        authorService.deleteAuthor(id);
        return ResponseEntity.ok("Author deleted successfully.");
    }
}
