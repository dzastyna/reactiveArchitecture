package ms.arqlib.catalogue;

import java.util.Collection;
import java.util.Optional;

import static ms.strings.S.$;

public class BooksApplicationService {
    private BooksRepository repository;

    public BooksApplicationService(BooksRepository repository) {
        this.repository = repository;
    }

    public Book addBook(String title, String author, String isbn, String publisher, int year, String category) {
        return repository.add(new Book(title, author, isbn, publisher, year, category));
    }

    public Collection<Book> findAll() {
        return repository.findAll();
    }

    public Collection<Book> findByTitle(String toSearch) {
        return repository.findByTitle(toSearch);
    }

    public Optional<Book> findById(long bookId) {
        return repository.findById(bookId);
    }

    public double ratingFor(long id) {
        Optional<Book> book = repository.findById(id);
        if (!book.isPresent()) {
            throw new BookValidationException($("Book id = %d doesn't exits. No rating available.", id));
        }

        return book.get().getRating();
    }

    public void rate(long bookId, int rating) {
        Optional<Book> returnedBook = repository.findById(bookId);
        if (!returnedBook.isPresent()) {
            throw new BookValidationException($("Book id = %d doesn't exits. Cannot be rated.", bookId));
        }

        Book book = returnedBook.get();
        book.rate(rating);
        repository.save(book);
    }

    public String findDescription(long bookId) {
        Optional<Book> book = this.repository.findById(bookId);
        if (!book.isPresent()) {
            throw new BookNotFoundException($("Book id = %d doesn't exits. No description available.", bookId));
        }

        return book.get().description();
    }
}
