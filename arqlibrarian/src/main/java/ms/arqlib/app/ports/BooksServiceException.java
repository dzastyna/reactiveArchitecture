package ms.arqlib.app.ports;

public class BooksServiceException extends RuntimeException {
    public BooksServiceException(String reason) {
        super(reason);
    }
}
