package me.mrixs.otus.spring.library.dao;

import me.mrixs.otus.spring.library.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthorDaoJdbcTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void findById() {
        Author author = authorDao.findById(1);
        assertEquals(1, author.getId());
        assertEquals("George Orwell", author.getName());
    }

    @Test
    void findAll() {
        List<Author> authors = authorDao.findAll();
        assertEquals(2, authors.size());
        assertEquals("George Orwell", authors.get(0).getName());
        assertEquals("Ray Bradbury", authors.get(1).getName());
    }

    @Test
    void save() {
        Author testAuthor = new Author("Test Author");
        Author author = authorDao.save(testAuthor);
        assertEquals(testAuthor.getName(), author.getName());
        assertNotEquals(0, author.getId());
    }

    @Test
    void findByName() {
        Author author = authorDao.findByName("George Orwell");
        assertEquals(1, author.getId());
        assertEquals("George Orwell", author.getName());
    }
}