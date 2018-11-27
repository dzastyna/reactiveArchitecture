package ms.arqlib.issues.adapters;

import ms.arqlib.issues.ports.BooksService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateBooksAdapter implements BooksService {
    private RestTemplate restTemplate;

    public RestTemplateBooksAdapter(@Qualifier("booksRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String findDescription(Long bookId) {
        return this.restTemplate.getForObject("/books/{bookId}/description", String.class, bookId);
    }
}

