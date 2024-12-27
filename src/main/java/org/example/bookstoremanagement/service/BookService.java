package org.example.bookstoremanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.bookstoremanagement.domain.Author;
import org.example.bookstoremanagement.domain.Book;
import org.example.bookstoremanagement.domain.BookFile;
import org.example.bookstoremanagement.domain.Genre;
import org.example.bookstoremanagement.exception.InvalidRequestException;
import org.example.bookstoremanagement.exception.ResourceNotFoundException;
import org.example.bookstoremanagement.repository.AuthorRepository;
import org.example.bookstoremanagement.repository.BookFileRepository;
import org.example.bookstoremanagement.repository.BookRepository;
import org.example.bookstoremanagement.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    private final GenreRepository genreRepository;

    @Autowired
    private final BookFileRepository bookFileRepository;

    // ------------------ CREATE/UPDATE/DELETE BOOK ------------------

    public Book createBook(String title, String authorName, Long authorId, Long genreId,
                           Double price, Integer stock) {
        log.info("Creating book: {}", title);

        if (title == null || title.isBlank()) {
            throw new InvalidRequestException("Book title cannot be blank.");
        }

        Book book = Book.builder()
                .title(title)
                .authorName(authorName)  // keep the string too if you want
                .price(price)
                .stock(stock)
                .build();

        // If authorId is provided, fetch from DB
        if (authorId != null) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + authorId));
            book.setAuthor(author);
        }

        // If genreId is provided, fetch from DB
        if (genreId != null) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + genreId));
            book.setGenre(genre);
        }

        return bookRepository.save(book);
    }


    public Book updateBook(Long bookId, String title, String authorName, Double price, Integer stock) {
        log.info("Updating book with ID: {}", bookId);
        Book book = getBookById(bookId);

        if (title != null && !title.isBlank()) {
            book.setTitle(title);
        }
        if (authorName != null && !authorName.isBlank()) {
            book.setAuthorName(authorName);
        }
        if (price != null) {
            book.setPrice(price);
        }
        if (stock != null) {
            book.setStock(stock);
        }

        return bookRepository.save(book);
    }

    public void deleteBook(Long bookId) {
        log.info("Deleting book with ID: {}", bookId);
        Book book = getBookById(bookId);
        bookRepository.delete(book);
    }

    // ------------------ GETTERS ------------------

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + bookId));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // ------------------ FILE UPLOAD/DOWNLOAD ------------------

    public void uploadFile(Long bookId, MultipartFile file) {
        log.info("Uploading file for book ID: {}", bookId);
        Book book = getBookById(bookId);

        if (file.isEmpty()) {
            throw new InvalidRequestException("Uploaded file is empty.");
        }

        try {
            // If there's an existing BookFile, remove or update it
            BookFile existingFile = book.getBookFile();
            if (existingFile != null) {
                // Update the existing file record
                existingFile.setFileName(file.getOriginalFilename());
                existingFile.setContentType(file.getContentType());
                existingFile.setFileSize(file.getSize());
                existingFile.setData(file.getBytes());
                bookFileRepository.save(existingFile);
            } else {
                // Create new BookFile
                BookFile bookFile = BookFile.builder()
                        .fileName(file.getOriginalFilename())
                        .contentType(file.getContentType())
                        .fileSize(file.getSize())
                        .data(file.getBytes())
                        .book(book)
                        .build();
                bookFileRepository.save(bookFile);
                book.setBookFile(bookFile);
                bookRepository.save(book);
            }
        } catch (IOException e) {
            throw new InvalidRequestException("Failed to read file data: " + e.getMessage());
        }
    }

    public BookFile downloadFile(Long bookId) {
        log.info("Downloading file for book ID: {}", bookId);
        Book book = getBookById(bookId);
        BookFile bookFile = book.getBookFile();
        if (bookFile == null) {
            throw new ResourceNotFoundException("No file found for this book.");
        }
        return bookFile;
    }
}
