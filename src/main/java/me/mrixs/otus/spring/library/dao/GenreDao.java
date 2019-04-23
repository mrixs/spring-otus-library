package me.mrixs.otus.spring.library.dao;

import me.mrixs.otus.spring.library.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre findById(long id);

    List<Genre> findAll();

    Genre save(Genre genre);

    Genre findByName(String name);

}
