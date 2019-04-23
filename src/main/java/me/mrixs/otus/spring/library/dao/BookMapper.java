package me.mrixs.otus.spring.library.dao;

import me.mrixs.otus.spring.library.domain.Book;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("title");
        return new Book(id, name, null, null);
    }
}
