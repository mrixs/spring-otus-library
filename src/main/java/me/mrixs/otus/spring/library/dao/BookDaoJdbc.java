package me.mrixs.otus.spring.library.dao;

import me.mrixs.otus.spring.library.domain.Author;
import me.mrixs.otus.spring.library.domain.Book;
import me.mrixs.otus.spring.library.domain.Genre;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BookDaoJdbc implements BookDao {

    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(AuthorDao authorDao, GenreDao genreDao, NamedParameterJdbcOperations jdbc) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.jdbc = jdbc;
    }

    @Override
    public Book findById(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Book result = jdbc.queryForObject("SELECT * FROM books WHERE id = :id", params, new BookMapper());
        result.setAuthors(getAuthorsByBookId(result.getId()));
        result.setGenres(getGenresByBookId(result.getId()));
        return result;
    }

    @Override
    public List<Book> findAll() {
        List<Book> result = jdbc.query("SELECT * FROM books", new BookMapper());
        result.forEach(book -> {
            book.setAuthors(getAuthorsByBookId(book.getId()));
            book.setGenres(getGenresByBookId(book.getId()));
        });
        return result;
    }

    @Override
    public Book save(Book book) {
        if (findByTitle(book.getTitle()) == null) {
            Map<String, String> params = new HashMap<>();
            params.put("bookTitle", book.getTitle());
            List<Author> authors = book.getAuthors();
            List<Genre> genres = book.getGenres();
            authors = authors.stream().map(author -> {
                authorDao.save(author);
                return authorDao.findByName(author.getName());
            }).collect(Collectors.toList());
            genres = genres.stream().map(genre -> {
                genreDao.save(genre);
                return genreDao.findByName(genre.getName());
            }).collect(Collectors.toList());
            jdbc.update("INSERT INTO books (title) VALUES (:bookTitle)", params);
            Book insertedBook = findByTitle(book.getTitle());
            authors.forEach(author -> {
                Map<String, Long> authorsBindingParams = new HashMap<>();
                authorsBindingParams.put("authorId", author.getId());
                authorsBindingParams.put("bookId", insertedBook.getId());
                jdbc.update("INSERT INTO authors_books (book_id, author_id) VALUES (:bookId, :authorId)", authorsBindingParams);
            });
            genres.forEach(genre -> {
                Map<String, Long> genresBindingParams = new HashMap<>();
                genresBindingParams.put("genreId", genre.getId());
                genresBindingParams.put("bookId", insertedBook.getId());
                jdbc.update("INSERT INTO genres_books (book_id, genre_id) VALUES (:bookId, :genreId)", genresBindingParams);
            });
        }
        return findByTitle(book.getTitle());
    }

    @Override
    public Book findByTitle(String title) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        Book result;
        try {
            result = jdbc.queryForObject("SELECT * FROM books WHERE title = :title", params, new BookMapper());
            result.setAuthors(getAuthorsByBookId(result.getId()));
            result.setGenres(getGenresByBookId(result.getId()));
        } catch (DataAccessException e) {
            return null;
        }

        return result;
    }

    private List<Author> getAuthorsByBookId(Long bookId) {
        Map<String, Long> params = new HashMap<>();
        params.put("bookId", bookId);
        return jdbc.queryForList("SELECT author_id FROM authors_books WHERE book_id = :bookId", params, Long.class)
                .stream()
                .map(authorDao::findById)
                .collect(Collectors.toList());
    }

    private List<Genre> getGenresByBookId(Long bookId) {
        Map<String, Long> params = new HashMap<>();
        params.put("bookId", bookId);
        return jdbc.queryForList("SELECT genre_id FROM genres_books WHERE book_id = :bookId", params, Long.class)
                .stream()
                .map(genreDao::findById)
                .collect(Collectors.toList());
    }
}
