package ms.arqlib.catalogue;

import java.util.Iterator;

public class BooksApplicationService {
    private BooksRepository repository;

    public BooksApplicationService(BooksRepository repository) {
        this.repository = repository;
    }

    public void addBook(String title, String author, String isbn, String publisher, int year, String category) {
        repository.add(new Book(title, author, isbn, publisher, year, category));
    }

    public Iterator<Book> findAll() {
        return repository.findAll();
    }

    public Iterator<Book> findByTitle(String toSearch) {
        return repository.findByTitle(toSearch);
    }

    public Book findById(long bookId) {
        return repository.findById(bookId);
    }

    public double computeRatingFor(long id) {
        Book book = repository.findById(id);

        return book.averageRating();
    }

    public void rate(long bookId, int rating) {
        Book book = repository.findById(bookId);
        book.rate(rating);
        repository.save(book);
    }

    public String findDescription(long bookId) {
        Book book = this.repository.findById(bookId);

        return book.descrption();
    }
}
