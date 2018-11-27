package ms.arqlib.issues;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.arqlib.issues.adapters.BookServiceErrorHandler;
import ms.arqlib.issues.adapters.RestTemplateBooksAdapter;
import ms.arqlib.issues.adapters.RestTemplateUsersAdapter;
import ms.arqlib.issues.adapters.UsersServiceErrorHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class Application {
    private final Log log = LogFactory.getLog(getClass());

    @Bean("booksRestTemplate")
    @LoadBalanced
    RestTemplate booksRestTemplate(RestTemplateBuilder builder, @Value("${books-service.url}") String url) {
        return builder
                .rootUri(url)
                .errorHandler(new BookServiceErrorHandler(new ObjectMapper()))
                .build();
    }

    @Bean("usersRestTemplate")
    @LoadBalanced
    RestTemplate usersRestTemplate(RestTemplateBuilder builder, @Value("${users-service.url}") String url) {
        return builder
                .rootUri(url)
                .errorHandler(new UsersServiceErrorHandler(new ObjectMapper()))
                .build();
    }

    @Bean
    public IssuesApplicationService issuesApplicationService(
            RestTemplateBooksAdapter booksService, RestTemplateUsersAdapter usersService, DiscoveryClient discoveryClient) {

	    IssuesApplicationService issuesApplicationService = new IssuesApplicationService(
                usersService,
                booksService,
                new MemoryIssuesRepository());

        return issuesApplicationService;
    }

    @Component
    class AppRunner implements ApplicationRunner {
        private IssuesApplicationService service;

        public AppRunner(IssuesApplicationService service) {
            this.service = service;
        }

        @Override
        public void run(ApplicationArguments args) throws Exception {
            log.debug("Started (issues rest)");

            service.issue(1L, 1L);
            service.issue(1L, 2L);
            service.issue(1L, 3L);
        }
    }

    // Alternative startup
    // @Component
    class AfterStartup implements ApplicationListener<ApplicationReadyEvent> {
        private IssuesApplicationService service;

        public AfterStartup(IssuesApplicationService service) {
            this.service = service;
        }

        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            log.debug("Started");

            service.issue(1L, 1L);
            service.issue(1L, 2L);
        }
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

