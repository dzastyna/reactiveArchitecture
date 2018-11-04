package ms.arqlib.catalogue;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/books")
class BooksController {
    private BooksApplicationService service;

    BooksController(BooksApplicationService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    Book bookOfId(@PathVariable("id") Long id) {
        return this.service.findById(id);
    }

    @GetMapping
    Collection<Book> search(
            @RequestParam(value = "title", defaultValue = "") String title) {

        Iterator<Book> booksIterator
                = (title !=null && !title.isEmpty())
                    ? this.service.findByTitle(title)
                    : this.service.findAll();

        List<Book> books = new ArrayList<>();
        booksIterator.forEachRemaining(books::add);

        return books;
    }

    @PostMapping
    void addBook(@RequestBody AddBookRequest request) {
        this.service.addBook(request.title, request.author, request.isbn, request.publisher, request.year, request.category);
    }

    @GetMapping("/{bookId}/rating")
    double bookRating(@PathVariable Long bookId) {
        return this.service.ratingFor(bookId);
    }

    @PostMapping("/ratings")
    void rate(@RequestBody RateBookRequest request) {
        this.service.rate(request.bookId, request.rating);
    }

    @GetMapping("/{bookId}/description")
    String bookDescription(@PathVariable Long bookId) {
        return this.service.findDescription(bookId);
    }
}

class AddBookRequest {
    final String title;
    final String author;
    final String isbn;
    final String publisher;
    final int year;
    final String category;

    public AddBookRequest(String title, String author, String isbn, String publisher, int year, String category) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.year = year;
        this.category = category;
    }
}

// TODO add userId
class RateBookRequest {
    final long bookId;
    final int rating;

    RateBookRequest(int bookId, int rating) {
        this.bookId = bookId;
        this.rating = rating;
    }
}
