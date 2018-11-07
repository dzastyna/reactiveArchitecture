package ms.arqlib;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.arqlib.app.Application;
import ms.arqlib.app.ConsoleIn;
import ms.arqlib.app.ConsoleOut;
import ms.arqlib.app.adapters.FeignIssuesServiceAdapter;
import ms.arqlib.app.adapters.FeignUsersServiceAdapter;
import ms.arqlib.app.adapters.RestTemplateBooksServiceAdapter;
import ms.arqlib.app.adapters.RestTemplateErrorHandler;
import ms.arqlib.catalogue.BooksApplicationService;
import ms.arqlib.catalogue.MemoryBooksRepository;
import ms.arqlib.issues.IssuesRepository;
import ms.arqlib.issues.MemoryIssuesRepository;
import ms.arqlib.users.MemoryUsersRepository;
import ms.arqlib.users.UsersApplicationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class Main {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateErrorHandler(new ObjectMapper()));
        return restTemplate;
    }

    @Bean
    CommandLineRunner run(
            RestTemplateBooksServiceAdapter bookServiceAdapter,
            FeignUsersServiceAdapter usersServiceAdapter,
            FeignIssuesServiceAdapter issuesServiceAdapter,
            ConfigurableApplicationContext context) {


        return args ->  {
                Application application = new Application(new ConsoleIn(), new ConsoleOut());
                application.setup(bookServiceAdapter);
                application.setup(usersServiceAdapter);
                application.setup(issuesServiceAdapter);

                application.start();
                SpringApplication.exit(context);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    private static BooksApplicationService createBooksApplicationService() {
        MemoryBooksRepository booksRepository = new MemoryBooksRepository();
        booksRepository.init();

        return new BooksApplicationService(booksRepository);
    }

    private static IssuesRepository createMemoryIssuesRepository()
    {
        MemoryIssuesRepository repo = new MemoryIssuesRepository();
        repo.init();

        return repo;
    }

    private static UsersApplicationService createUsersService()
    {
        MemoryUsersRepository repo = new MemoryUsersRepository();
        repo.init();

        return new UsersApplicationService(repo);
    }
}