package ms.arqlib.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public final static String SECURED = "secured";
    public final static String DEBUG = "debug";

    @Profile(Application.DEBUG)
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setIncludeHeaders(true);
        return loggingFilter;
    }

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

@Profile("!" + Application.SECURED)
@Configuration
class WebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .anyRequest().permitAll()
                    .and()
                .csrf()
                    .disable();
    }
}

@Profile(Application.SECURED)
@EnableResourceServer
@Configuration
class ApplicationConfiguration {

}