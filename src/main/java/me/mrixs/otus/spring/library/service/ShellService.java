package me.mrixs.otus.spring.library.service;

import me.mrixs.otus.spring.library.dao.AuthorDao;
import me.mrixs.otus.spring.library.dao.BookDao;
import me.mrixs.otus.spring.library.dao.GenreDao;
import me.mrixs.otus.spring.library.domain.Author;
import me.mrixs.otus.spring.library.domain.Book;
import me.mrixs.otus.spring.library.domain.Genre;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
public class ShellService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public ShellService(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @ShellMethod(value = "List all books", key = "listBooks")
    public List<Book> listAllBooks() {
        return bookDao.findAll();
    }

    @ShellMethod(value = "List all authors", key = "listAuthors")
    public List<Author> listAllAuthors() {
        return authorDao.findAll();
    }

    @ShellMethod(value = "List all genres", key = "listGenres")
    public List<Genre> listAllGenres() {
        return genreDao.findAll();
    }

    @ShellMethod(value = "Save book", key = "saveBook")
    public Book saveBook(@ShellOption String title, @ShellOption String authors, @ShellOption String genres) {
        List<Author> authorList = Stream.of(authors.split(","))
                .map(String::trim)
                .map(Author::new)
                .collect(Collectors.toList());
        List<Genre> genreList = Stream.of(genres.split(","))
                .map(String::trim)
                .map(Genre::new)
                .collect(Collectors.toList());
        return bookDao.save(new Book(title, authorList, genreList));
    }
}
