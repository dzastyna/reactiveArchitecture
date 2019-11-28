package ms.arqlib.issues;

import ms.arqlib.issues.adapters.BooksChannel;
import ms.arqlib.issues.adapters.RestTemplateBooksAdapter;
import ms.arqlib.issues.adapters.RestTemplateUsersAdapter;
import ms.rest.service.ErrorInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
@EnableFeignClients
@EnableBinding(BooksChannel.class)
public class Application {

	@Bean
    public IssuesApplicationService issuesApplicationService(
            RestTemplateBooksAdapter booksService, RestTemplateUsersAdapter usersService) {

	    IssuesApplicationService issuesApplicationService = new IssuesApplicationService(
                usersService,
                booksService,
                new MemoryIssuesRepository());

        // sample issues for demo purposes
        issuesApplicationService.issue(1L, 1L);
        issuesApplicationService.issue(1L, 2L);

        return issuesApplicationService;
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

    @Bean
    public CommandLineRunner run(ApplicationContext appContext) {
        return args -> {

            String[] beans = appContext.getBeanDefinitionNames();
            Arrays.stream(beans).sorted().forEach(System.out::println);

        };
    }
}

@RestController
@RequestMapping("/issues")
class IssuesController {
    private final Log log = LogFactory.getLog(getClass());

    @ExceptionHandler(IssueValidationException.class)
    public ResponseEntity issueValidation(IssueValidationException ex) {
        ErrorInfo error = new ErrorInfo(ex);
        log.error(error);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    private IssuesApplicationService applicationService;

    IssuesController(IssuesApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public void issue(@RequestBody IssueRequest request) {
        this.applicationService.issue(request.userId, request.bookId);
    }

    @GetMapping("/{id}/issued")
    public boolean issued(@PathVariable("id") Long bookId) {
        return this.applicationService.issued(bookId);
    }

    @GetMapping
    public Collection<Issue> findAll() {
        return this.applicationService.findAll();
    }
}

class IssueRequest {
    long userId;
    long bookId;

    public IssueRequest(long userId, long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
}

