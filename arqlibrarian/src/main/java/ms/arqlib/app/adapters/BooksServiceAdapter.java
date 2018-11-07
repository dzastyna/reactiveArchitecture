package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.AddBookRequest;
import ms.arqlib.app.ports.Book;
import ms.arqlib.app.ports.BooksService;
import ms.arqlib.app.ports.RateBookRequest;
import ms.arqlib.catalogue.BooksApplicationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BooksServiceAdapter implements BooksService {
    private BooksApplicationService booksApplicationService;

    public BooksServiceAdapter(BooksApplicationService booksApplicationService) {
        this.booksApplicationService = booksApplicationService;
    }

    ms.arqlib.app.ports.Book adapt(ms.arqlib.catalogue.Book book) {
        return new ms.arqlib.app.ports.Book(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublisher(),
                book.getYear(),
                book.getCategory(),
                book.getRating());
    }

    Collection<Book> adapt(Collection<ms.arqlib.catalogue.Book> books) {
        List<ms.arqlib.app.ports.Book> result = new ArrayList<>();

        for (ms.arqlib.catalogue.Book book : books) {
            result.add(adapt(book));
        }

        return result;
    }

    @Override
    public Book addBook(AddBookRequest request) {
        return adapt(this.booksApplicationService.addBook(
                request.title, request.author, request.isbn, request.publisher, request.year, request.category));
    }

    @Override
    public Collection<Book> findAll() {
        return adapt(this.booksApplicationService.findAll());
    }

    @Override
    public Collection<Book> findByTitle(String toSearch) {
        return adapt(this.booksApplicationService.findByTitle(toSearch));
    }

    @Override
    public Optional<Book> findById(long bookId) {
        Optional<ms.arqlib.catalogue.Book> found = this.booksApplicationService.findById(bookId);
        if (!found.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(adapt(found.get()));
    }

    @Override
    public double computeRatingFor(long id) {
        return this.booksApplicationService.ratingFor(id);
    }

    @Override
    public void rate(RateBookRequest request) {
        this.booksApplicationService.rate(request.bookId, request.rating);
    }
}
