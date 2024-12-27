package org.example.bookstoremanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoremanagement.domain.Genre;
import org.example.bookstoremanagement.exception.ResourceNotFoundException;
import org.example.bookstoremanagement.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;

    public Genre createGenre(String name) {
        log.info("Creating a new genre: {}", name);
        Genre genre = Genre.builder()
                .name(name)
                .build();
        return genreRepository.save(genre);
    }

    public Genre updateGenre(Long genreId, String name) {
        log.info("Updating genre with ID: {}", genreId);
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + genreId));

        if (name != null && !name.isBlank()) {
            genre.setName(name);
        }
        return genreRepository.save(genre);
    }

    public Genre getGenreById(Long genreId) {
        log.info("Retrieving genre with ID: {}", genreId);
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + genreId));
    }

    public List<Genre> getAllGenres() {
        log.info("Retrieving all genres");
        return genreRepository.findAll();
    }

    public void deleteGenre(Long genreId) {
        log.info("Deleting genre with ID: {}", genreId);
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + genreId));
        genreRepository.delete(genre);
        log.info("Genre deleted successfully");
    }
}
