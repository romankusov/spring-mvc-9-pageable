package org.example.pageable.service;

import org.example.pageable.model.Author;

public interface AuthorService {
    Author getAuthorById(Integer id);
    Author createAuthor(String name);
}
