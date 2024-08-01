package org.example.pageable.controller;

import org.example.pageable.model.Book;
import org.example.pageable.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void testGetBookById() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTotalPrice(500);

        when(bookService.getBookById(1)).thenReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.totalPrice", is(500)));
    }

    @Test
    void testGetAllBooksWithPagination() throws Exception {
        Book book1 = new Book();
        book1.setId(1);
        book1.setTotalPrice(500);

        Book book2 = new Book();
        book2.setId(2);
        book2.setTotalPrice(1000);

        Pageable pageable = PageRequest.of(0, 2, Sort.by("totalPrice").ascending());
        Page<Book> bookPage = new PageImpl<>(Arrays.asList(book1, book2), pageable, 2);

        when(bookService.getAllBooks(pageable)).thenReturn(bookPage);

        mockMvc.perform(get("/books/")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "totalPrice,asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].totalPrice", is(500)))
                .andExpect(jsonPath("$.content[1].totalPrice", is(1000)));
    }

    @Test
    void testAddBook() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTotalPrice(500);

        when(bookService.addBook(1, "New Book", 500)).thenReturn(book);

        mockMvc.perform(post("/books/")
                        .param("authorId", "1")
                        .param("name", "New Book")
                        .param("totalPrice", "500")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.totalPrice", is(500)));
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk());

        verify(bookService).deleteBook(1);
    }
}