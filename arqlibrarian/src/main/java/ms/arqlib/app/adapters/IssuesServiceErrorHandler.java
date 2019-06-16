package ms.arqlib.app.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.rest.client.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static ms.strings.S.from;

public class IssuesServiceErrorHandler implements ResponseErrorHandler {
    private ObjectMapper objectMapper;

    public IssuesServiceErrorHandler(ObjectMapper objectMapper) {
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
