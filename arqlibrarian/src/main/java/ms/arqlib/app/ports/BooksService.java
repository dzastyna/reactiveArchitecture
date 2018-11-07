package ms.arqlib.app.ports;

import java.util.Collection;
import java.util.Optional;

public interface BooksService {
    Book addBook(AddBookRequest request);

    Collection<Book> findAll();

    Collection<Book> findByTitle(String toSearch);

    Optional<Book> findById(long bookId);

    double computeRatingFor(long id);

    void rate(RateBookRequest request);
}
