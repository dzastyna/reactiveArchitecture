package ms.arqlib.issues.adapters;

import ms.arqlib.issues.ports.UsersService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateUsersAdapter implements UsersService {
    private RestTemplate restTemplate;

    public RestTemplateUsersAdapter(@Qualifier("usersRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String findDescription(Long bookId) {
        return this.restTemplate.getForObject("/users/{bookId}/description", String.class, bookId);
    }
}
