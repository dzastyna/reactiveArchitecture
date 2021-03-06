package ms.arqlibrarian.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@EnableDiscoveryClient
@EnableZuulProxy
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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@Profile(Application.SECURED)
@Configuration
@EnableResourceServer
@EnableOAuth2Sso
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .csrf()
                    .disable();
    }

    @Bean
    // enable if want to use discovery in oauth configuration (eg. security.oauth2.resource.userInfoUri=...)
    // @LoadBalanced
    OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory templateFactory) {
        return templateFactory.getUserInfoRestTemplate();
    }
}

@Profile("!" + Application.SECURED)
@Configuration
class NonSecureConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .anyRequest().permitAll()
                    .and()
                .csrf()
                    .disable();
    }
}