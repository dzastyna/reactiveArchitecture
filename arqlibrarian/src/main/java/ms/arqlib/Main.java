package ms.arqlib;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.arqlib.app.Application;
import ms.arqlib.app.ConsoleIn;
import ms.arqlib.app.ConsoleOut;
import ms.arqlib.app.adapters.*;
import ms.arqlib.catalogue.BooksApplicationService;
import ms.arqlib.catalogue.MemoryBooksRepository;
import ms.arqlib.issues.IssuesRepository;
import ms.arqlib.issues.MemoryIssuesRepository;
import ms.arqlib.users.MemoryUsersRepository;
import ms.arqlib.users.UsersApplicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class Main {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    RestTemplate booksRestTemplate(@Value("${books-service.url}") String booksServiceUrl) {
        return new RestTemplateBuilder()
                .rootUri(booksServiceUrl)
                .errorHandler(new BooksServiceErrorHandler(new ObjectMapper()))
                .build();
    }

    @Bean
    RestTemplate issuesRestTemplate(@Value("${issues-service.url}") String issuesServiceUrl) {
            return new RestTemplateBuilder()
                    .rootUri(issuesServiceUrl)
                    .errorHandler(new IssuesServiceErrorHandler(new ObjectMapper()))
                    .build();
    }

    @Bean
    CommandLineRunner run(
            RestTemplateBooksServiceAdapter bookServiceAdapter,
            FeignUsersServiceAdapter usersServiceAdapter,
            RestTemplateIssuesServiceAdapter issuesServiceAdapter,
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