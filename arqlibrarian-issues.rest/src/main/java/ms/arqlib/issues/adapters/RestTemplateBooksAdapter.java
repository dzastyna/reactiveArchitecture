package ms.arqlib.issues.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.arqlib.issues.ports.BookNotFoundException;
import ms.arqlib.issues.ports.BooksService;
import ms.rest.client.ErrorInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static ms.strings.S.from;

@Service
public class RestTemplateBooksAdapter implements BooksService {
    private RestTemplate restTemplate;

    public RestTemplateBooksAdapter(@Value("${books-service.url}") String serviceUrl) {
        this.restTemplate = new RestTemplateBuilder()
                .rootUri(serviceUrl)
                .errorHandler(new BookServiceErrorHandler(new ObjectMapper()))
                .build();
    }

    @Override
    public String findDescription(Long bookId) {
        return this.restTemplate.getForObject("/books/{bookId}/description", String.class, bookId);
    }

    private class BookServiceErrorHandler implements ResponseErrorHandler {
        private ObjectMapper objectMapper;

        public BookServiceErrorHandler(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            ErrorInfo error = objectMapper.readValue(from(response.getBody()), ErrorInfo.class);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new BookNotFoundException(error.getMessage());
            }
        }
    }
}
