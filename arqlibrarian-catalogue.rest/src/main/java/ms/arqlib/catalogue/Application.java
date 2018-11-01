package ms.arqlib.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	@Bean
	public BooksApplicationService booksService() {
		MemoryBooksRepository repository = new MemoryBooksRepository();
		repository.init();

		return new BooksApplicationService(repository);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

@RestController
@RequestMapping("/books")
class BooksController {
    private BooksApplicationService service;

    BooksController(BooksApplicationService service) {
        this.service = service;
    }

    @GetMapping
    List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        this.service.findAll().forEachRemaining(books::add);
        return books;
    }

    @GetMapping("/{id}")
    Book findById(@PathVariable("id") long id) {
        return this.service.findById(id);
    }
}