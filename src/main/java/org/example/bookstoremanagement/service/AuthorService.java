package org.example.bookstoremanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoremanagement.domain.Author;
import org.example.bookstoremanagement.exception.ResourceNotFoundException;
import org.example.bookstoremanagement.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author createAuthor(String name, String bio) {
        log.info("Creating a new author: {}", name);
        Author author = Author.builder()
                .name(name)
                .bio(bio)
                .build();
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long authorId, String name, String bio) {
        log.info("Updating author with ID: {}", authorId);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + authorId));

        if (name != null && !name.isBlank()) {
            author.setName(name);
        }
        if (bio != null) {
            author.setBio(bio);
        }
        return authorRepository.save(author);
    }

    public Author getAuthorById(Long authorId) {
        log.info("Retrieving author with ID: {}", authorId);
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + authorId));
    }

    public List<Author> getAllAuthors() {
        log.info("Retrieving all authors");
        return authorRepository.findAll();
    }

    public void deleteAuthor(Long authorId) {
        log.info("Deleting author with ID: {}", authorId);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + authorId));
        authorRepository.delete(author);
        log.info("Author deleted successfully");
    }
}
