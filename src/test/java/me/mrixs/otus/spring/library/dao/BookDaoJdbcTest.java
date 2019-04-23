package me.mrixs.otus.spring.library.dao;

import me.mrixs.otus.spring.library.domain.Author;
import me.mrixs.otus.spring.library.domain.Book;
import me.mrixs.otus.spring.library.domain.Genre;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class BookDaoJdbcTest {

    @Autowired
    private BookDao bookDao;


    @Test
    void findById() {
        Book book = bookDao.findById(1);
        assertEquals(1, book.getId());
        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthors().get(0).getName());
        assertEquals("dystopia", book.getGenres().get(0).getName());
    }

    @Test
    void findAll() {
        List<Book> books = bookDao.findAll();
        assertEquals(2, books.size());
        assertNotNull(books.get(0).getTitle());
        assertTrue(books.get(1).getId() != 0);
        assertEquals(1, books.get(1).getAuthors().size());
        assertEquals(1, books.get(1).getGenres().size());
    }

    @Test
    void save() {
        List<Author> authors = new ArrayList<>();
        authors.add(0, new Author("Test Author"));
        List<Genre> genres = new ArrayList<>();
        genres.add(0, new Genre("Test Genre"));

        Book book = new Book("Test Author", authors, genres);
        Book testBook = bookDao.save(book);
        assertEquals(testBook.getTitle(), book.getTitle());
        assertEquals(testBook.getAuthors().size(), book.getAuthors().size());
        assertEquals(testBook.getGenres().size(), book.getGenres().size());

    }

    @Test
    void findByTitle() {
        Book book = bookDao.findByTitle("1984");
        assertEquals(1, book.getId());
        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthors().get(0).getName());
        assertEquals("dystopia", book.getGenres().get(0).getName());
    }
}