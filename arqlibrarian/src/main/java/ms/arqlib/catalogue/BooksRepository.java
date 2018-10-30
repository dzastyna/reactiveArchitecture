package ms.arqlib.catalogue;

import java.util.Iterator;

public interface BooksRepository {
    void add(Book book);

    Iterator<Book> findAll();

    Iterator<Book> findByTitle(String title);

    Book findById(long id);

    void save(Book book);
}
