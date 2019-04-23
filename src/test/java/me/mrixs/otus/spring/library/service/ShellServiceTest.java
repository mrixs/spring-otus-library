package me.mrixs.otus.spring.library.service;

import me.mrixs.otus.spring.library.dao.AuthorDao;
import me.mrixs.otus.spring.library.dao.BookDao;
import me.mrixs.otus.spring.library.dao.GenreDao;
import me.mrixs.otus.spring.library.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShellServiceTest {

    @MockBean
    BookDao bookDao;
    @MockBean
    AuthorDao authorDao;
    @MockBean
    GenreDao genreDao;

    @Autowired
    ShellService shellService;


    @Test
    void saveBook() {
        String title = "Test book";
        String authors = "Test Author, Author Test";
        String genres = "Test Genre";
        when(bookDao.save(any())).then(returnsFirstArg());

        Book testBook = shellService.saveBook(title, authors, genres);

        assertEquals(title, testBook.getTitle());
        assertEquals("Test Author", testBook.getAuthors().get(0).getName());
        assertEquals("Author Test", testBook.getAuthors().get(1).getName());
        assertEquals("Test Genre", testBook.getGenres().get(0).getName());
        assertEquals(2, testBook.getAuthors().size());
        assertEquals(1, testBook.getGenres().size());
    }
}