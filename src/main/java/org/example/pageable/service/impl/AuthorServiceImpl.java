package org.example.pageable.service.impl;

import lombok.AllArgsConstructor;
import org.example.pageable.exceptions.customexceptions.NotFoundException;
import org.example.pageable.model.Author;
import org.example.pageable.repository.AuthorRepository;
import org.example.pageable.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Автор с id " + id + " Не найден"));
    }

    @Override
    public Author createAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        author = authorRepository.save(author);
        return author;
    }
}
