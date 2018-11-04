package ms.arqlib.app.ports;

import java.util.Collection;

public interface BooksService {
    void addBook(AddBookRequest request);

    Collection<Book> findAll();

    Collection<Book> findByTitle(String toSearch);

    Book findById(long bookId);

    double computeRatingFor(long id);

    void rate(RateBookRequest request);
}
