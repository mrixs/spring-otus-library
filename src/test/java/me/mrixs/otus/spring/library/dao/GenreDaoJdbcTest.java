package me.mrixs.otus.spring.library.dao;

import me.mrixs.otus.spring.library.domain.Genre;
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
class GenreDaoJdbcTest {

    @Autowired
    GenreDao genreDao;

    @Test
    void findById() {
        Genre genre = genreDao.findById(1);
        assertEquals(1, genre.getId());
        assertEquals("dystopia", genre.getName());
    }

    @Test
    void findAll() {
        List<Genre> genres = genreDao.findAll();
        assertEquals(3, genres.size());
        assertEquals("dystopia", genres.get(0).getName());
        assertEquals("political fiction", genres.get(1).getName());
        assertEquals("social science fiction", genres.get(2).getName());

    }

    @Test
    void save() {
        Genre testGenre = new Genre("Test Genre");
        Genre genre = genreDao.save(testGenre);
        assertEquals(testGenre.getName(), genre.getName());
        assertNotEquals(0, genre.getId());
    }

    @Test
    void findByName() {
        Genre genre = genreDao.findByName("dystopia");
        assertEquals(1, genre.getId());
        assertEquals("dystopia", genre.getName());

    }
}