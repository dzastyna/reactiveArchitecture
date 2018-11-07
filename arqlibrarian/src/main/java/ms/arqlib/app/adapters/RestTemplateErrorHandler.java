package ms.arqlib.app.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ms.arqlib.app.ports.BooksServiceException;
import ms.rest.client.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static ms.strings.S.from;
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private ObjectMapper mapper;

    public RestTemplateErrorHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR)
                && httpResponse.getStatusCode() != HttpStatus.NOT_FOUND;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        ErrorInfo error = mapper.readValue(from(httpResponse.getBody()), ErrorInfo.class);

        if (httpResponse.getStatusCode().is5xxServerError()) {
            throw new BooksServiceException(error.getMessage());

        } else if (httpResponse.getStatusCode().is4xxClientError()) {
            throw new BooksServiceException(error.getMessage());
        }
    }
}
