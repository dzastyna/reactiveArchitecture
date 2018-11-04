package ms.arqlib.issues;

import ms.arqlib.issues.adapters.RestBooksAdapter;
import ms.arqlib.issues.adapters.RestUsersAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@SpringBootApplication
@EnableFeignClients
public class Application {

	@Bean
    public IssuesApplicationService issuesApplicationService(
            RestBooksAdapter booksService, RestUsersAdapter usersService) {
        IssuesApplicationService issuesApplicationService = new IssuesApplicationService(
                usersService,
                booksService,
                new MemoryIssuesRepository());

        issuesApplicationService.issue(1L, 1L);
        issuesApplicationService.issue(1L, 2L);

        return issuesApplicationService;
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

@RestController
@RequestMapping("/issues")
class IssuesController {
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

