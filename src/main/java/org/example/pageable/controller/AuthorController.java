package org.example.pageable.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.pageable.model.Author;
import org.example.pageable.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/{id}")
    public ResponseEntity<Author> getUserById(@PathVariable @Valid Integer id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping("/{name}")
    public ResponseEntity<Author> createAuthor(@PathVariable String name) {
        return ResponseEntity.ok(authorService.createAuthor(name));
    }

}
