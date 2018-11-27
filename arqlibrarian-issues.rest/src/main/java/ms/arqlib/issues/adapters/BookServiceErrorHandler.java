package ms.arqlib.issues.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.arqlib.issues.ports.BookNotFoundException;
import ms.rest.client.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static ms.strings.S.from;

public class BookServiceErrorHandler implements ResponseErrorHandler {
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
