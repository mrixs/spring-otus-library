DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS genres_books;
DROP TABLE IF EXISTS authors_books;

CREATE TABLE books(
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        title VARCHAR(255) UNIQUE
);

CREATE TABLE authors(
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255) UNIQUE
);

CREATE TABLE genres(
        id BIGINT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255) UNIQUE
);

CREATE TABLE genres_books(
        book_id BIGINT,
        genre_id BIGINT,
        FOREIGN KEY (book_id) REFERENCES books(id),
        FOREIGN KEY (genre_id) REFERENCES genres(id)
);

CREATE TABLE authors_books(
        book_id BIGINT,
        author_id BIGINT,
        FOREIGN KEY (book_id) REFERENCES books(id),
        FOREIGN KEY (author_id) REFERENCES authors(id)
);