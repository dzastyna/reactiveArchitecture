package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.AddBookRequest;
import ms.arqlib.app.ports.Book;
import ms.arqlib.app.ports.BooksService;
import ms.arqlib.app.ports.RateBookRequest;
import ms.arqlib.catalogue.BooksApplicationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

    List<ms.arqlib.app.ports.Book> adapt(Iterator<ms.arqlib.catalogue.Book> iterator) {
        List<ms.arqlib.app.ports.Book> result = new ArrayList<>();

        while (iterator.hasNext()) {
            result.add(adapt(iterator.next()));
        }

        return result;
    }

    @Override
    public void addBook(AddBookRequest request) {
        this.booksApplicationService.addBook(
                request.title, request.author, request.isbn, request.publisher, request.year, request.category);
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
    public Book findById(long bookId) {
        return adapt(this.booksApplicationService.findById(bookId));
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
