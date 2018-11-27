package ms.arqlib.app.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.arqlib.app.ports.IssueBookRequest;
import ms.arqlib.app.ports.IssuesService;
import ms.arqlib.issues.Issue;
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
public class RestTemplateIssuesServiceAdapter implements IssuesService {
    private RestTemplate restTemplate;

    public RestTemplateIssuesServiceAdapter(@Value("${issues-service.url}") String booksServiceUrl) {
        this.restTemplate = new RestTemplateBuilder()
                .rootUri(booksServiceUrl)
                .errorHandler(new UsersServiceErrorHandler(new ObjectMapper()))
                .build();
    }

    @Override
    public Issue issue(IssueBookRequest request) {
        return this.restTemplate.postForObject("/issues", request, Issue.class);
    }

    @Override
    public boolean issued(long bookId) {
        return this.restTemplate.getForObject("/issues/{id}/issued", boolean.class, bookId);
    }

    private class UsersServiceErrorHandler implements ResponseErrorHandler {
        private ObjectMapper objectMapper;

        public UsersServiceErrorHandler(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return clientHttpResponse.getStatusCode().is4xxClientError() || clientHttpResponse.getStatusCode().is5xxServerError();
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            ErrorInfo error = objectMapper.readValue(from(response.getBody()), ErrorInfo.class);

            if (response.getStatusCode().is5xxServerError()) {
                throw new IssueServiceException(error.getMessage());

            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new IssueNotFoundException(error.getMessage());

            } else if (response.getStatusCode().is4xxClientError()) {
                throw new IssueServiceException(error.getMessage());
            }
        }
    }
}
