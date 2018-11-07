package ms.arqlib.catalogue;

import ms.rest.service.ErrorInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.*;

import static ms.strings.S.$;

@RestController
@RequestMapping("/books")
class BooksController {
    private final Log log = LogFactory.getLog(getClass());

    private BooksApplicationService service;

    BooksController(BooksApplicationService service) {
        this.service = service;
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity bookNotFound(BookNotFoundException ex) {
        ErrorInfo error = new ErrorInfo(ex);
        log.error(error);

       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BookValidationException.class)
    public ResponseEntity bookValidation(BookValidationException ex) {
        ErrorInfo error = new ErrorInfo(ex);
        log.error(error);


        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity generalException(Throwable ex) {
        ErrorInfo error = new ErrorInfo(ex);
        log.error(error, ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<?> options() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST)
                .build();
    }

    @GetMapping("/{id}")
    ResponseEntity<Book> bookOfId(@PathVariable("id") Long id) {
        Optional<Book> book = this.service.findById(id);
        if (!book.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book.get());
    }

    @GetMapping
    Collection<Book> search(
            @RequestParam(value = "title", defaultValue = "") String title) {

        Collection<Book> books
                = (title !=null && !title.isEmpty())
                    ? this.service.findByTitle(title)
                    : this.service.findAll();

        return books;
    }

    @PostMapping
    ResponseEntity<Book> addBook(@RequestBody AddBookRequest request) {
        Book book = this.service.addBook(
                request.title, request.author, request.isbn, request.publisher, request.year, request.category);

        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}")
                .buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(uri).body(book);
    }

    @GetMapping("/{bookId}/rating")
    double bookRating(@PathVariable Long bookId) {
        return this.service.ratingFor(bookId);
    }

    @PostMapping("/ratings")
    ResponseEntity<ResponseMessage> rate(@RequestBody RateBookRequest request) {
        this.service.rate(request.bookId, request.rating);

        return ResponseEntity.ok()
                .body(new ResponseMessage($("Book id=%d rated successfully with: %d", request.bookId, request.rating)));
    }

    @GetMapping("/{bookId}/description")
    String bookDescription(@PathVariable Long bookId) {
        return this.service.findDescription(bookId);
    }
}

class ResponseMessage {
    private String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
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
