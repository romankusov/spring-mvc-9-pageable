package org.example.pageable.service.impl;

import lombok.AllArgsConstructor;
import org.example.pageable.exceptions.customexceptions.NotFoundException;
import org.example.pageable.model.Author;
import org.example.pageable.model.Book;
import org.example.pageable.repository.AuthorRepository;
import org.example.pageable.repository.BookRepository;
import org.example.pageable.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBookById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Книга с id " + id + " не найдена"));
    }

    @Override
    public Book addBook(Integer authorId, String name, int totalPrice) {
        Author author = Optional.of(authorRepository.getReferenceById(authorId))
                .orElseThrow(() -> new NotFoundException("Автор с id=" + authorId +" не найдена"));
        Book book = new Book();
        book.setAuthor(author);
        book.setTotalPrice(totalPrice);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
}
