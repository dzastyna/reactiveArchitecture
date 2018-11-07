package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.AddBookRequest;
import ms.arqlib.app.ports.Book;
import ms.arqlib.app.ports.BooksService;
import ms.arqlib.app.ports.RateBookRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@Deprecated
@FeignClient(url="${books-service.url}", name="${books-service.name}")
public interface FeignBooksServiceAdapter extends BooksService {
    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/books")
    Book addBook(@RequestBody AddBookRequest request);

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/books")
    Collection<Book> findAll();

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/books")
    Collection<Book> findByTitle(@RequestParam("title") String title);

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/books/{id}")
    Optional<Book> findById(@PathVariable("id") long bookId);

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/books/{id}/rating")
    double computeRatingFor(@PathVariable("id") long id);

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/books/ratings")
    void rate(@RequestBody RateBookRequest request);
}
