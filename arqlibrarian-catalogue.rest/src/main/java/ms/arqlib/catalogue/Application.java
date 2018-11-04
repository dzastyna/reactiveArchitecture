package ms.arqlib.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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