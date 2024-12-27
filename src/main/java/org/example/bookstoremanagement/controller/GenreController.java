// File: src/main/java/org/example/bookstoremanagement/controller/GenreController.java

package org.example.bookstoremanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoremanagement.domain.Genre;
import org.example.bookstoremanagement.dto.GenreRequest;
import org.example.bookstoremanagement.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles CRUD operations for Genre.
 */
@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@Slf4j
public class GenreController {

    private final GenreService genreService;

    /**
     * Creates a new Genre.
     *
     * @param genreRequest the request body containing genre details
     * @return the created Genre
     */
    @PostMapping
    public ResponseEntity<Genre> createGenre(
            @Valid @RequestBody GenreRequest genreRequest
    ) {
        log.info("REST request to create genre: {}", genreRequest.getName());
        Genre genre = genreService.createGenre(genreRequest.getName());
        return ResponseEntity.ok(genre);
    }

    /**
     * Retrieves a Genre by ID.
     *
     * @param id the ID of the Genre
     * @return the Genre with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        log.info("REST request to get genre by ID: {}", id);
        Genre genre = genreService.getGenreById(id);
        return ResponseEntity.ok(genre);
    }

    /**
     * Retrieves all Genres.
     *
     * @return a list of all Genres
     */
    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        log.info("REST request to get all genres");
        List<Genre> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

    /**
     * Updates an existing Genre.
     *
     * @param id           the ID of the Genre to update
     * @param genreRequest the request body containing updated genre details
     * @return the updated Genre
     */
    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(
            @PathVariable Long id,
            @Valid @RequestBody GenreRequest genreRequest
    ) {
        log.info("REST request to update genre ID: {}", id);
        Genre updated = genreService.updateGenre(id, genreRequest.getName());
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a Genre by ID.
     *
     * @param id the ID of the Genre to delete
     * @return a confirmation message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Long id) {
        log.info("REST request to delete genre ID: {}", id);
        genreService.deleteGenre(id);
        return ResponseEntity.ok("Genre deleted successfully.");
    }
}
