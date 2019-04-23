package me.mrixs.otus.spring.library.dao;

import me.mrixs.otus.spring.library.domain.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Author findById(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject("SELECT * FROM authors WHERE id = :id", params, new AuthorMapper());
    }

    @Override
    public List<Author> findAll() {
        return jdbc.query("SELECT * FROM authors", new AuthorMapper());

    }

    @Override
    public Author save(Author author) {
        if (findByName(author.getName()) == null) {
            Map<String, String> params = new HashMap<>();
            params.put("authorName", author.getName());
            jdbc.update("INSERT INTO authors (name) VALUES (:authorName)", params);
        }
        return findByName(author.getName());
    }

    @Override
    public Author findByName(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        Author result;
        try {
            result = jdbc.queryForObject("SELECT * FROM authors WHERE name=:name", params, new AuthorMapper());
        } catch (DataAccessException e) {
            return null;
        }
        return result;
    }
}

