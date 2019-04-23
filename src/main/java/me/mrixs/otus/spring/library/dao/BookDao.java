package me.mrixs.otus.spring.library.dao;

import me.mrixs.otus.spring.library.domain.Book;

import java.util.List;

public interface BookDao {
    Book findById(long id);

    List<Book> findAll();

    Book save(Book book);

    Book findByTitle(String title);
}
