INSERT INTO books (id, title) VALUES(1, '1984');
INSERT INTO books (id, title) VALUES(2, 'Fahrenheit 451');

INSERT INTO authors (id, name) VALUES(1, 'George Orwell');
INSERT INTO authors (id, name) VALUES(2, 'Ray Bradbury');

INSERT INTO genres (id, name) VALUES(1, 'dystopia');
INSERT INTO genres (id, name) VALUES(2, 'political fiction');
INSERT INTO genres (id, name) VALUES(3, 'social science fiction');

INSERT INTO authors_BOOKS (book_id, author_id) VALUES(1,1);
INSERT INTO genres_BOOKS (book_id, genre_id) VALUES(1,1);
INSERT INTO genres_BOOKS (book_id, genre_id) VALUES(1,2);
INSERT INTO genres_BOOKS (book_id, genre_id) VALUES(1,3);

INSERT INTO authors_BOOKS (book_id, author_id) VALUES(2,2);
INSERT INTO genres_BOOKS (book_id, genre_id) VALUES(2,1);

