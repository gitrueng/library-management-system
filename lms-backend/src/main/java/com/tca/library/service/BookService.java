package com.tca.library.service;

import com.tca.library.jpa.Books;
import com.tca.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository booksRepository) {
        this.bookRepository = booksRepository;
    }

    @Transactional(readOnly = true)
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Books> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public Books createBook(Books book) {
        validateBook(book);
        return bookRepository.save(book);
    }

    public Optional<Books> updateBook(Integer id, Books updatedBook) {
        return bookRepository.findById(id)
            .map(existingBook -> {
                updatedBook.setId(id);
                validateBook(updatedBook);
                return bookRepository.save(updatedBook);
            });
    }

    public boolean deleteBook(Integer id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Books> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    private void validateBook(Books book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty");
        }
    }
}
