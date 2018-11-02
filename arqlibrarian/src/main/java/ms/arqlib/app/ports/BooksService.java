package ms.arqlib.app.ports;

import java.util.Collection;

public interface BooksService {
    void addBook(String title, String author, String isbn, String publisher, int year, String category);

    Collection<Book> findAll();

    Collection<Book> findByTitle(String toSearch);

    Book findById(long bookId);

    double computeRatingFor(long id);

    void rate(long bookId, int rating);
}
