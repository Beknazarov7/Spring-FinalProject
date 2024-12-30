package org.example.bookstoremanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoremanagement.domain.Book;
import org.example.bookstoremanagement.domain.BookFile;
import org.example.bookstoremanagement.dto.BookRequest;
import org.example.bookstoremanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    @Autowired
    private final BookService bookService;

    // ------------------ USER ACCESS ------------------

    // Everyone can list all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Book> books = bookService.getBooksPaginated(page, size);
        return ResponseEntity.ok(books);
    }

    // Everyone can view a book detail
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // Everyone can download the file if it exists
    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        BookFile bookFile = bookService.downloadFile(id);
        ByteArrayResource resource = new ByteArrayResource(bookFile.getData());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(bookFile.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + bookFile.getFileName() + "\"")
                .body(resource);
    }

    // ------------------ ADMIN ACCESS ------------------

    // Only admins can create a book
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequest bookRequest) {
        Book book = bookService.createBook(
                bookRequest.getTitle(),
                bookRequest.getAuthorName(),
                bookRequest.getAuthorId(),
                bookRequest.getGenreId(),
                bookRequest.getPrice(),
                bookRequest.getStock()
        );
        return ResponseEntity.ok(book);
    }

    // Only admins can update a book
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest bookRequest
    ) {
        Book updated = bookService.updateBook(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthorName(),
                bookRequest.getPrice(),
                bookRequest.getStock()
        );
        return ResponseEntity.ok(updated);
    }

    // Only admins can delete a book
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully.");
    }

    // Only admins can upload a file
    @PostMapping("/{id}/upload")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> uploadFile(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        bookService.uploadFile(id, file);
        return ResponseEntity.ok("File uploaded successfully.");
    }
}
