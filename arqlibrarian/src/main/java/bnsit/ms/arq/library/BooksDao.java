package bnsit.ms.arq.library;

import java.util.Iterator;

public interface BooksDao {
    void insert(Book book);

    Iterator<Book> findAll();

    Iterator<Book> findByTitle(String title);

    Book findById(long id);

    void save(Book book);
}
