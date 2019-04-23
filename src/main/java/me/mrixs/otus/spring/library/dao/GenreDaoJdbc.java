package me.mrixs.otus.spring.library.dao;

import me.mrixs.otus.spring.library.domain.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Genre findById(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject("SELECT * FROM genres WHERE id = :id", params, new GenreMapper());
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.query("SELECT * FROM genres", new GenreMapper());
    }

    @Override
    public Genre save(Genre genre) {
        if (findByName(genre.getName()) == null) {
            Map<String, String> params = new HashMap<>();
            params.put("genreName", genre.getName());
            jdbc.update("INSERT INTO genres (name) VALUES (:genreName)", params);
        }
        return findByName(genre.getName());
    }

    @Override
    public Genre findByName(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        Genre result;
        try {
            result = jdbc.queryForObject("SELECT * FROM genres WHERE name=:name", params, new GenreMapper());
        } catch (DataAccessException e) {
            return null;
        }
        return result;
    }
}
