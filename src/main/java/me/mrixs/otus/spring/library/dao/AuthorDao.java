package me.mrixs.otus.spring.library.dao;

import me.mrixs.otus.spring.library.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author findById(long id);

    List<Author> findAll();

    Author save(Author a);

    Author findByName(String name);
}
