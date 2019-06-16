package ms.arqlib.app.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.arqlib.app.ports.AddBookRequest;
import ms.arqlib.app.ports.Book;
import ms.arqlib.app.ports.BooksService;
import ms.arqlib.app.ports.RateBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class RestTemplateBooksServiceAdapter implements BooksService {
    private RestTemplate restTemplate;

    public RestTemplateBooksServiceAdapter(@Autowired @Qualifier("booksRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Book addBook(AddBookRequest request) {
        return this.restTemplate.postForObject("/books", request, Book.class);
    }

    @Override
    public Collection<Book> findAll() {
        return Arrays.asList(this.restTemplate.getForObject("/books", Book[].class));
    }

    @Override
    public Collection<Book> findByTitle(String toSearch) {
        return Arrays.asList(this.restTemplate.getForObject("/books?title={title}", Book[].class, toSearch));
    }

    @Override
    public Optional<Book> findById(long bookId) {

        try {
            ResponseEntity<Book> bookEntity
                    = this.restTemplate.exchange("/books/{bookId}", HttpMethod.GET, null, Book.class, bookId);

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
        return this.restTemplate.getForObject("/books/{id}/rating", double.class, id);
    }

    @Override
    public void rate(RateBookRequest request) {
        this.restTemplate.postForLocation("/books/ratings", request);
    }
}
