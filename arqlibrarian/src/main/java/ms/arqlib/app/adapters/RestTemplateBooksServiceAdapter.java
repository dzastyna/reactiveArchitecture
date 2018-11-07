package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.AddBookRequest;
import ms.arqlib.app.ports.Book;
import ms.arqlib.app.ports.BooksService;
import ms.arqlib.app.ports.RateBookRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Optional;

@Service
public class RestTemplateBooksServiceAdapter implements BooksService {
    private RestTemplate restTemplate;
    private FeignBooksServiceAdapter feignAdapter;

    public RestTemplateBooksServiceAdapter(RestTemplate restTemplate, FeignBooksServiceAdapter feignAdapter) {
        this.restTemplate = restTemplate;
        this.feignAdapter = feignAdapter;
    }

    @Override
    public Book addBook(AddBookRequest request) {
        return null;
    }

    @Override
    public Collection<Book> findAll() {
        return feignAdapter.findAll();
    }

    @Override
    public Collection<Book> findByTitle(String toSearch) {
        return feignAdapter.findByTitle(toSearch);
    }

    @Override
    public Optional<Book> findById(long bookId) {

        try {
            ResponseEntity<Book> bookEntity
                    = this.restTemplate.exchange("http://localhost:6700/books/{bookId}", HttpMethod.GET, null, Book.class, bookId);

            if (bookEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Optional.empty();
            }

            return Optional.of(bookEntity.getBody());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Optional.empty();
            }
            else {
                throw e;
            }
        }
    }

    @Override
    public double computeRatingFor(long id) {
        return feignAdapter.computeRatingFor(id);
    }

    @Override
    public void rate(RateBookRequest request) {
        this.restTemplate.postForLocation("http://localhost:6700/books/ratings", request);
    }
}
