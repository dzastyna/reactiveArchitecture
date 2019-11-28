package ms.arqlib.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBinding(BooksChannel.class)
public class Application {

	@Bean
	public BooksApplicationService booksService(Publisher publisher) {
		MemoryBooksRepository repository = new MemoryBooksRepository();
		repository.init();

		return new BooksApplicationService(repository, publisher);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
