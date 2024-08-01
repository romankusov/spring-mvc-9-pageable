package org.example.pageable.service;

import org.example.pageable.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<Book> getAllBooks(Pageable pageable);
    Book getBookById(Integer id);
    Book addBook(Integer authorId, String name, int totalPrice);
    void deleteBook(Integer id);
}
