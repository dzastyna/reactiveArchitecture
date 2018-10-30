package bnsit.ms.arq.library;

import java.util.Iterator;

public class BooksManager {
    private BooksDao dao;

    public BooksManager(BooksDao dao) {
        this.dao = dao;
    }

    public void create(String title, String author, String isbn, String publisher, int year, String category) {
        dao.insert(new Book(title, author, isbn, publisher, year, category));
    }

    public Iterator<Book> findAll() {
        return dao.findAll();
    }

    public Iterator<Book> findByTitle(String toSearch) {
        return dao.findByTitle(toSearch);
    }

    public Book findById(long bookId) {
        return dao.findById(bookId);
    }

    public double computeRatingFor(long id) {
        Book book = dao.findById(id);

        if (book.getRatings().size() == 0)
        {
            return -1.0;
        }

        return book.getRatings().stream()
                .mapToDouble(r -> r.getRating())
                .average().getAsDouble();

    }

    public void rate(long bookId, int rating) {
        Book book = dao.findById(bookId);

        book.add(new SingleRating(rating));

        dao.save(book);
    }
}
