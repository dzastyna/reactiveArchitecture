package ms.arqlib.catalogue;

import java.util.Collection;
import java.util.Optional;

public interface BooksRepository {
    Book add(Book book);

    Collection<Book> findAll();

    Collection<Book> findByTitle(String title);

    Optional<Book> findById(long id);

    void save(Book book);
}
